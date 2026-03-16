package com.SE.ITHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PortfolioImageResponseDto {

    private UUID id;
    private UUID portfolioItemId;
    private String imageUrl;
    private Boolean isCover;
    private Integer sortOrder;
    private String altTextOverride;
    private LocalDateTime createdAt;
}