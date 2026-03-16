package com.SE.ITHub.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ServicePlanResponseDto {

    private String serviceType;
    private String planName;
    private BigDecimal price;
    private String priceType;
    private String description;
    private String features;
    private boolean isFeatured;
    private boolean isActive;

}
