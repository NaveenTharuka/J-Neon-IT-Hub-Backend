package com.SE.ITHub.mapper;

import com.SE.ITHub.dto.ServiceRequestDto;
import com.SE.ITHub.dto.ServiceResponseDto;
import com.SE.ITHub.dto.ServiceUpdateDto;
import com.SE.ITHub.model.Services;
import org.springframework.stereotype.Component;

@Component
public class ServicesMapper {

    public Services toEntity(ServiceRequestDto reqDto) {

        if (reqDto.getTitle() == null || reqDto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required and cannot be empty");
        }
        if (reqDto.getSlug() == null || reqDto.getSlug().trim().isEmpty()) {
            throw new IllegalArgumentException("Slug is required and cannot be empty");
        }

        return Services.builder()
                .title(reqDto.getTitle())
                .slug(reqDto.getSlug())
                .shortDescription(reqDto.getShortDescription())
                .icon(reqDto.getIcon())
                .isActive(reqDto.isActive())
                .sortOrder(0)
                .build();
    }

    public ServiceResponseDto toResponse(Services service) {
        return ServiceResponseDto.builder()
                .id(service.getId())
                .title(service.getTitle())
                .slug(service.getSlug())
                .shortDescription(service.getShortDescription())
                .icon(service.getIcon())
                .isActive(service.isActive())
                .sortOrder(service.getSortOrder())
                .build();
    }

    public Services updateEntity(Services service, ServiceUpdateDto updateDto) {
        if (updateDto.getTitle() != null && !updateDto.getTitle().trim().isEmpty()) {
            service.setTitle(updateDto.getTitle());
        }
        if (updateDto.getSlug() != null && !updateDto.getSlug().trim().isEmpty()) {
            service.setSlug(updateDto.getSlug());
        }
        if (updateDto.getShortDescription() != null) {
            service.setShortDescription(updateDto.getShortDescription());
        }
        if (updateDto.getIcon() != null) {
            service.setIcon(updateDto.getIcon());
        }
        // Only update if different
        if (updateDto.isActive() != service.isActive()) {
            service.setActive(updateDto.isActive());
        }

        return service;
    }
}