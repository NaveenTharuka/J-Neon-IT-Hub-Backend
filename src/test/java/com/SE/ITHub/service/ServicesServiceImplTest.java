package com.SE.ITHub.service;

import com.SE.ITHub.dto.ServiceRequestDto;
import com.SE.ITHub.dto.ServiceResponseDto;
import com.SE.ITHub.dto.ServiceUpdateDto;
import com.SE.ITHub.exception.ServiceNotFoundException;
import com.SE.ITHub.mapper.ServicePlanMapper;
import com.SE.ITHub.mapper.ServicesMapper;
import com.SE.ITHub.model.Services;
import com.SE.ITHub.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicesServiceImplTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ServicesMapper servicesMapper;

    @Mock
    private ServicePlanMapper servicePlanMapper;

    @InjectMocks
    private ServicesServiceImpl servicesService;

    private UUID testId;
    private ServiceRequestDto requestDto;
    private ServiceResponseDto responseDto;
    private ServiceUpdateDto updateDto;
    private Services service;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();

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

        service = Services.builder()
                .id(testId)
                .title("Test Service")
                .slug("test-service")
                .shortDescription("Test Description")
                .icon("test-icon")
                .isActive(true)
                .sortOrder(0)
                .build();
    }

    @Test
    void createService_ShouldReturnResponseDto() {
        when(servicesMapper.toEntity(requestDto)).thenReturn(service);
        when(serviceRepository.save(service)).thenReturn(service);
        when(servicesMapper.toResponse(service)).thenReturn(responseDto);

        ServiceResponseDto result = servicesService.createService(requestDto);

        assertNotNull(result);
        assertEquals(testId, result.getId());
        assertEquals("Test Service", result.getTitle());
        assertEquals("test-service", result.getSlug());
    }

    @Test
    void getServiceById_ShouldReturnService() {
        when(serviceRepository.findById(testId)).thenReturn(Optional.of(service));
        when(servicesMapper.toResponse(service)).thenReturn(responseDto);

        ServiceResponseDto result = servicesService.getServiceById(testId);

        assertNotNull(result);
        assertEquals(testId, result.getId());
        assertEquals("Test Service", result.getTitle());
    }

    @Test
    void getServiceById_NotFound_ShouldThrowException() {
        when(serviceRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(ServiceNotFoundException.class, () -> {
            servicesService.getServiceById(testId);
        });
    }

    @Test
    void getAllServices_ShouldReturnList() {
        List<Services> services = Arrays.asList(service);
        when(serviceRepository.findAll()).thenReturn(services);
        when(servicesMapper.toResponse(service)).thenReturn(responseDto);

        List<ServiceResponseDto> results = servicesService.getAllServices();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(testId, results.get(0).getId());
    }

    @Test
    void updateService_ShouldReturnUpdatedService() {
        when(serviceRepository.findById(testId)).thenReturn(Optional.of(service));
        when(servicesMapper.updateEntity(service, updateDto)).thenReturn(service);
        when(serviceRepository.save(service)).thenReturn(service);
        when(servicesMapper.toResponse(service)).thenReturn(responseDto);

        ServiceResponseDto result = servicesService.updateService(testId, updateDto);

        assertNotNull(result);
        assertEquals(testId, result.getId());
    }

    @Test
    void updateService_NotFound_ShouldThrowException() {
        when(serviceRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(ServiceNotFoundException.class, () -> {
            servicesService.updateService(testId, updateDto);
        });
    }

    @Test
    void deleteService_ShouldDeleteService() {
        when(serviceRepository.findById(testId)).thenReturn(Optional.of(service));
        doNothing().when(serviceRepository).delete(service);

        servicesService.deleteService(testId);

        verify(serviceRepository, times(1)).delete(service);
    }

    @Test
    void deleteService_NotFound_ShouldThrowException() {
        when(serviceRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(ServiceNotFoundException.class, () -> {
            servicesService.deleteService(testId);
        });
    }
}