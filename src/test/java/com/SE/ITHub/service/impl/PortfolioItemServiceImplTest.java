package com.SE.ITHub.service.impl;

import com.SE.ITHub.dto.PortfolioItemRequestDto;
import com.SE.ITHub.dto.PortfolioItemResponseDto;
import com.SE.ITHub.model.PortfolioItem;
import com.SE.ITHub.model.Services;
import com.SE.ITHub.repository.PortfolioItemRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PortfolioItemServiceImplTest {

    @Mock
    private PortfolioItemRepository portfolioItemRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PortfolioItemServiceImpl portfolioItemService;

    private UUID testId;
    private UUID serviceId;
    private PortfolioItemRequestDto requestDto;
    private PortfolioItem portfolioItem;
    private Services service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(portfolioItemService, "entityManager", entityManager);

        testId = UUID.randomUUID();
        serviceId = UUID.randomUUID();

        service = new Services();
        service.setId(serviceId);

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

        portfolioItem = new PortfolioItem();
        portfolioItem.setId(testId);
        portfolioItem.setTitle("Test Portfolio");
        portfolioItem.setSlug("test-portfolio");
        portfolioItem.setClientName("Test Client");
        portfolioItem.setSummary("Test Summary");
        portfolioItem.setDescription("Test Description");
        portfolioItem.setProjectDate(LocalDate.now());
        portfolioItem.setProjectUrl("http://test.com");
        portfolioItem.setIsPublished(true);
        portfolioItem.setSortOrder(1);
        portfolioItem.setService(service);
        portfolioItem.setCreatedAt(LocalDateTime.now());
        portfolioItem.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createPortfolioItem_ShouldReturnResponseDto() {
        // Mock the repository calls
        when(portfolioItemRepository.existsBySlug(anyString())).thenReturn(false);

        // Mock EntityManager to return a service reference
        when(entityManager.getReference(Services.class, serviceId)).thenReturn(service);

        // Mock the save operation
        when(portfolioItemRepository.save(any(PortfolioItem.class))).thenReturn(portfolioItem);

        // Execute the test
        PortfolioItemResponseDto response = portfolioItemService.createPortfolioItem(requestDto);

        // Verify the results
        assertNotNull(response);
        assertEquals(testId, response.getId());
        assertEquals("Test Portfolio", response.getTitle());
        assertEquals("test-portfolio", response.getSlug());
        assertEquals(serviceId, response.getServiceId());

        // Verify interactions
        verify(portfolioItemRepository).existsBySlug("test-portfolio");
        verify(entityManager).getReference(Services.class, serviceId);
        verify(portfolioItemRepository).save(any(PortfolioItem.class));
    }

    @Test
    void createPortfolioItem_WithNullTitle_ShouldThrowException() {
        requestDto.setTitle(null);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> portfolioItemService.createPortfolioItem(requestDto));

        assertEquals("Portfolio item title is required", exception.getMessage());

        // Verify no interactions with repository or entity manager
        verify(portfolioItemRepository, never()).existsBySlug(anyString());
        verify(entityManager, never()).getReference(any(), any());
        verify(portfolioItemRepository, never()).save(any());
    }

    @Test
    void createPortfolioItem_WithDuplicateSlug_ShouldThrowException() {
        when(portfolioItemRepository.existsBySlug(anyString())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> portfolioItemService.createPortfolioItem(requestDto));

        assertEquals("Slug already exists", exception.getMessage());

        // Verify repository was called but save was not
        verify(portfolioItemRepository).existsBySlug("test-portfolio");
        verify(entityManager, never()).getReference(any(), any());
        verify(portfolioItemRepository, never()).save(any());
    }

    @Test
    void createPortfolioItem_WithoutServiceId_ShouldCreateWithoutService() {
        // Remove service ID from request
        requestDto.setServiceId(null);

        when(portfolioItemRepository.existsBySlug(anyString())).thenReturn(false);
        when(portfolioItemRepository.save(any(PortfolioItem.class))).thenReturn(portfolioItem);

        PortfolioItemResponseDto response = portfolioItemService.createPortfolioItem(requestDto);

        assertNotNull(response);
        verify(portfolioItemRepository).existsBySlug("test-portfolio");
        // Verify entityManager.getReference was NOT called
        verify(entityManager, never()).getReference(any(), any());
        verify(portfolioItemRepository).save(any(PortfolioItem.class));
    }

    @Test
    void getPortfolioItemById_ShouldReturnItem() {
        when(portfolioItemRepository.findById(testId)).thenReturn(Optional.of(portfolioItem));

        PortfolioItemResponseDto response = portfolioItemService.getPortfolioItemById(testId);

        assertNotNull(response);
        assertEquals(testId, response.getId());
        assertEquals("Test Portfolio", response.getTitle());
        verify(portfolioItemRepository).findById(testId);
    }

    @Test
    void getPortfolioItemById_NotFound_ShouldThrowException() {
        when(portfolioItemRepository.findById(testId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> portfolioItemService.getPortfolioItemById(testId));

        assertEquals("Portfolio item not found", exception.getMessage());
        verify(portfolioItemRepository).findById(testId);
    }

    @Test
    void getAllPortfolioItems_ShouldReturnList() {
        List<PortfolioItem> items = Arrays.asList(portfolioItem);
        when(portfolioItemRepository.findAll()).thenReturn(items);

        List<PortfolioItemResponseDto> responses = portfolioItemService.getAllPortfolioItems();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(testId, responses.get(0).getId());
        verify(portfolioItemRepository).findAll();
    }

    @Test
    void updatePortfolioItem_ShouldReturnUpdatedItem() {
        // Mock finding the existing item
        when(portfolioItemRepository.findById(testId)).thenReturn(Optional.of(portfolioItem));

        // Mock slug uniqueness check
        when(portfolioItemRepository.existsBySlugAndIdNot(anyString(), eq(testId))).thenReturn(false);

        // Mock EntityManager to return service reference
        when(entityManager.getReference(Services.class, serviceId)).thenReturn(service);

        // Mock save operation
        when(portfolioItemRepository.save(any(PortfolioItem.class))).thenReturn(portfolioItem);

        PortfolioItemResponseDto response = portfolioItemService.updatePortfolioItem(testId, requestDto);

        assertNotNull(response);
        assertEquals(testId, response.getId());

        // Verify all interactions
        verify(portfolioItemRepository).findById(testId);
        verify(portfolioItemRepository).existsBySlugAndIdNot("test-portfolio", testId);
        verify(entityManager).getReference(Services.class, serviceId);
        verify(portfolioItemRepository).save(any(PortfolioItem.class));
    }

    @Test
    void updatePortfolioItem_WithDuplicateSlug_ShouldThrowException() {
        when(portfolioItemRepository.findById(testId)).thenReturn(Optional.of(portfolioItem));
        when(portfolioItemRepository.existsBySlugAndIdNot(anyString(), eq(testId))).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> portfolioItemService.updatePortfolioItem(testId, requestDto));

        assertEquals("Slug already exists", exception.getMessage());

        // Verify findById and exists check were called, but save was not
        verify(portfolioItemRepository).findById(testId);
        verify(portfolioItemRepository).existsBySlugAndIdNot("test-portfolio", testId);
        verify(entityManager, never()).getReference(any(), any());
        verify(portfolioItemRepository, never()).save(any());
    }

    @Test
    void updatePortfolioItem_NotFound_ShouldThrowException() {
        when(portfolioItemRepository.findById(testId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> portfolioItemService.updatePortfolioItem(testId, requestDto));

        assertEquals("Portfolio item not found", exception.getMessage());
        verify(portfolioItemRepository).findById(testId);
        verify(portfolioItemRepository, never()).existsBySlugAndIdNot(anyString(), any());
        verify(entityManager, never()).getReference(any(), any());
        verify(portfolioItemRepository, never()).save(any());
    }

    @Test
    void deletePortfolioItem_ShouldDeleteItem() {
        when(portfolioItemRepository.findById(testId)).thenReturn(Optional.of(portfolioItem));
        doNothing().when(portfolioItemRepository).delete(portfolioItem);

        portfolioItemService.deletePortfolioItem(testId);

        verify(portfolioItemRepository).findById(testId);
        verify(portfolioItemRepository).delete(portfolioItem);
    }

    @Test
    void deletePortfolioItem_NotFound_ShouldThrowException() {
        when(portfolioItemRepository.findById(testId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> portfolioItemService.deletePortfolioItem(testId));

        assertEquals("Portfolio item not found", exception.getMessage());
        verify(portfolioItemRepository).findById(testId);
        verify(portfolioItemRepository, never()).delete(any());
    }
}