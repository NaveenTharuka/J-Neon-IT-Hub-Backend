package com.SE.ITHub.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PortfolioImageRequestDto {

    private UUID portfolioItemId;
    private String imageUrl;
    private Boolean isCover;
    private Integer sortOrder;
    private String altTextOverride;
}