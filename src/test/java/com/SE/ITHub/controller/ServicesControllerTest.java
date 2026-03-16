package com.SE.ITHub.controller;

import com.SE.ITHub.dto.ServiceRequestDto;
import com.SE.ITHub.dto.ServiceResponseDto;
import com.SE.ITHub.dto.ServiceUpdateDto;
import com.SE.ITHub.service.ServicesServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
public class ServicesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServicesServiceImpl servicesService;

    @InjectMocks
    private ServicesController servicesController;

    private ObjectMapper objectMapper;
    private UUID testId;
    private ServiceRequestDto requestDto;
    private ServiceResponseDto responseDto;
    private ServiceUpdateDto updateDto;

    @BeforeEach
    void setUp() {
        // Create ObjectMapper instance manually
        objectMapper = new ObjectMapper().findAndRegisterModules();

        testId = UUID.randomUUID();

        mockMvc = MockMvcBuilders.standaloneSetup(servicesController).build();

        requestDto = new ServiceRequestDto();
        requestDto.setTitle("Test Service");
        requestDto.setSlug("test-service");
        requestDto.setShortDescription("Test Description");
        requestDto.setIcon("test-icon");
        requestDto.setActive(true);

        responseDto = ServiceResponseDto.builder()
                .id(testId)
                .title("Test Service")
                .slug("test-service")
                .shortDescription("Test Description")
                .icon("test-icon")
                .sortOrder(0)
                .build();

        updateDto = new ServiceUpdateDto(
                "Updated Service",
                "updated-icon",
                "updated-service",
                "Updated Description",
                5,
                true
        );
    }

    @Test
    void getAllServices_ShouldReturnList() throws Exception {
        List<ServiceResponseDto> services = Arrays.asList(responseDto);
        when(servicesService.getAllServices()).thenReturn(services);

        mockMvc.perform(get("/api/services/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testId.toString()))
                .andExpect(jsonPath("$[0].title").value("Test Service"));
    }

    @Test
    void getServiceById_ShouldReturnService() throws Exception {
        when(servicesService.getServiceById(testId)).thenReturn(responseDto);

        mockMvc.perform(get("/api/services/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testId.toString()))
                .andExpect(jsonPath("$.title").value("Test Service"));
    }

    @Test
    void deleteService_ShouldReturnOk() throws Exception {
        doNothing().when(servicesService).deleteService(testId);

        mockMvc.perform(delete("/api/services/{id}", testId))
                .andExpect(status().isOk());
    }
}