package com.SE.ITHub.controller;

import com.SE.ITHub.dto.PortfolioItemRequestDto;
import com.SE.ITHub.dto.PortfolioItemResponseDto;
import com.SE.ITHub.service.PortfolioItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PortfolioItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PortfolioItemService portfolioItemService;

    @InjectMocks
    private PortfolioItemController portfolioItemController;

    private ObjectMapper objectMapper;
    private UUID testId;
    private UUID serviceId;
    private PortfolioItemRequestDto requestDto;
    private PortfolioItemResponseDto responseDto;

    @BeforeEach
    void setUp() {
        // Create ObjectMapper instance manually
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // This registers JavaTimeModule for Java 8 date/time support

        mockMvc = MockMvcBuilders.standaloneSetup(portfolioItemController).build();

        testId = UUID.randomUUID();
        serviceId = UUID.randomUUID();

        requestDto = new PortfolioItemRequestDto();
        requestDto.setTitle("Test Portfolio");
        requestDto.setSlug("test-portfolio");
        requestDto.setClientName("Test Client");
        requestDto.setSummary("Test Summary");
        requestDto.setDescription("Test Description");
        requestDto.setProjectDate(LocalDate.now());
        requestDto.setProjectUrl("http://test.com");
        requestDto.setIsPublished(true);
        requestDto.setSortOrder(1);
        requestDto.setServiceId(serviceId);

        responseDto = new PortfolioItemResponseDto(
                testId,
                "Test Portfolio",
                "test-portfolio",
                "Test Client",
                "Test Summary",
                "Test Description",
                serviceId,
                LocalDate.now(),
                "http://test.com",
                true,
                1,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void createPortfolioItem_ShouldReturnCreatedItem() throws Exception {
        when(portfolioItemService.createPortfolioItem(any(PortfolioItemRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/portfolio-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.title").value("Test Portfolio"))
                .andExpect(jsonPath("$.slug").value("test-portfolio"))
                .andExpect(jsonPath("$.serviceId").value(serviceId.toString()));
    }

    @Test
    void getAllPortfolioItems_ShouldReturnList() throws Exception {
        List<PortfolioItemResponseDto> items = Arrays.asList(responseDto);
        when(portfolioItemService.getAllPortfolioItems()).thenReturn(items);

        mockMvc.perform(get("/api/portfolio-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testId.toString()))
                .andExpect(jsonPath("$[0].title").value("Test Portfolio"))
                .andExpect(jsonPath("$[0].slug").value("test-portfolio"));
    }

    @Test
    void getPortfolioItemById_ShouldReturnItem() throws Exception {
        when(portfolioItemService.getPortfolioItemById(testId)).thenReturn(responseDto);

        mockMvc.perform(get("/api/portfolio-items/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.title").value("Test Portfolio"));
    }

    @Test
    void updatePortfolioItem_ShouldReturnUpdatedItem() throws Exception {
        when(portfolioItemService.updatePortfolioItem(eq(testId), any(PortfolioItemRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/portfolio-items/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.title").value("Test Portfolio"));
    }

    @Test
    void deletePortfolioItem_ShouldReturnNoContent() throws Exception {
        doNothing().when(portfolioItemService).deletePortfolioItem(testId);

        mockMvc.perform(delete("/api/portfolio-items/{id}", testId))
                .andExpect(status().isNoContent());
    }
}