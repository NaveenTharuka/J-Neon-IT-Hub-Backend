package com.SE.ITHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PortfolioItemResponseDto {
    private UUID id;
    private String title;
    private String slug;
    private String clientName;
    private String summary;
    private String description;
    private UUID serviceId;
    private LocalDate projectDate;
    private String projectUrl;
    private Boolean isPublished;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}