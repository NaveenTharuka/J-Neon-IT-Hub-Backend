package com.SE.ITHub.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
public class ServicePlanResponseDto {

    private UUID id;
    private String serviceType;
    private String planName;
    private BigDecimal price;
    private String priceType;
    private String description;
    private String features;
    private Boolean isFeatured;
    private Boolean isActive;

}
