package com.SE.ITHub.mapper;

import com.SE.ITHub.dto.ServiceRequestDto;
import com.SE.ITHub.dto.ServiceResponseDto;
import com.SE.ITHub.dto.ServiceUpdateDto;
import com.SE.ITHub.model.Services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ServicesMapperTest {

    private ServicesMapper servicesMapper;

    @BeforeEach
    void setUp() {
        servicesMapper = new ServicesMapper();
    }

    @Test
    void toEntity_ShouldMapRequestDtoToEntity() {
        ServiceRequestDto requestDto = new ServiceRequestDto();
        requestDto.setTitle("Test Service");
        requestDto.setSlug("test-service");
        requestDto.setShortDescription("Test Description");
        requestDto.setIcon("test-icon");
        requestDto.setActive(true);

        Services service = servicesMapper.toEntity(requestDto);

        assertNotNull(service);
        assertEquals("Test Service", service.getTitle());
        assertEquals("test-service", service.getSlug());
        assertEquals("Test Description", service.getShortDescription());
        assertEquals("test-icon", service.getIcon());
        assertTrue(service.isActive());
        assertEquals(0, service.getSortOrder());
    }

    @Test
    void toEntity_WithEmptyTitle_ShouldThrowException() {
        ServiceRequestDto requestDto = new ServiceRequestDto();
        requestDto.setTitle("");
        requestDto.setSlug("test-service");

        assertThrows(IllegalArgumentException.class, () -> {
            servicesMapper.toEntity(requestDto);
        });
    }

    @Test
    void toEntity_WithEmptySlug_ShouldThrowException() {
        ServiceRequestDto requestDto = new ServiceRequestDto();
        requestDto.setTitle("Test Service");
        requestDto.setSlug("");

        assertThrows(IllegalArgumentException.class, () -> {
            servicesMapper.toEntity(requestDto);
        });
    }

    @Test
    void toResponse_ShouldMapEntityToResponseDto() {
        UUID id = UUID.randomUUID();
        Services service = Services.builder()
                .id(id)
                .title("Test Service")
                .slug("test-service")
                .shortDescription("Test Description")
                .icon("test-icon")
                .isActive(true)
                .sortOrder(5)
                .build();

        ServiceResponseDto responseDto = servicesMapper.toResponse(service);

        assertNotNull(responseDto);
        assertEquals(id, responseDto.getId());
        assertEquals("Test Service", responseDto.getTitle());
        assertEquals("test-service", responseDto.getSlug());
        assertEquals("Test Description", responseDto.getShortDescription());
        assertEquals("test-icon", responseDto.getIcon());
        assertEquals(5, responseDto.getSortOrder());
    }

    @Test
    void updateEntity_ShouldUpdateAllFields() {
        Services service = Services.builder()
                .id(UUID.randomUUID())
                .title("Original Title")
                .slug("original-slug")
                .shortDescription("Original Description")
                .icon("original-icon")
                .isActive(false)
                .sortOrder(0)
                .build();

        ServiceUpdateDto updateDto = new ServiceUpdateDto(
                "Updated Title",
                "updated-icon",
                "updated-slug",
                "Updated Description",
                10,
                true
        );

        Services updated = servicesMapper.updateEntity(service, updateDto);

        assertNotNull(updated);
        assertEquals("Updated Title", updated.getTitle());
        assertEquals("updated-slug", updated.getSlug());
        assertEquals("Updated Description", updated.getShortDescription());
        assertEquals("updated-icon", updated.getIcon());
        assertTrue(updated.isActive());
    }

    @Test
    void updateEntity_WithNullFields_ShouldKeepOriginalValues() {
        Services service = Services.builder()
                .id(UUID.randomUUID())
                .title("Original Title")
                .slug("original-slug")
                .shortDescription("Original Description")
                .icon("original-icon")
                .isActive(false)
                .sortOrder(0)
                .build();

        ServiceUpdateDto updateDto = new ServiceUpdateDto(
                null,
                null,
                null,
                null,
                0,
                false
        );

        Services updated = servicesMapper.updateEntity(service, updateDto);

        assertNotNull(updated);
        assertEquals("Original Title", updated.getTitle());
        assertEquals("original-slug", updated.getSlug());
        assertEquals("Original Description", updated.getShortDescription());
        assertEquals("original-icon", updated.getIcon());
        assertFalse(updated.isActive());
    }
}