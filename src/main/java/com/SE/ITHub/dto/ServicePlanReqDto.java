package com.SE.ITHub.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ServicePlanReqDto {


    private UUID ServiceId;

    @NotNull
    private String PlanName;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String priceType;

    @NotNull
    private String currency;

    @NotNull
    private String billingPeriod;

    private String description;

    private String features;

    private boolean isFeatured;

    private int sortOrder;

    private boolean isActive;

}
