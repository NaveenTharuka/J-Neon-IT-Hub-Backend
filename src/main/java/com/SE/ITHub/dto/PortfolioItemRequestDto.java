package com.SE.ITHub.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class PortfolioItemRequestDto {
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
}