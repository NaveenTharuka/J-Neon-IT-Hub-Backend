package com.SE.ITHub.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ServicePlanReqDto {

    private UUID serviceId;  // Changed from ServiceId to serviceId (camelCase)

    @NotNull
    private String planName;  // Changed from PlanName to planName (camelCase)

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

    private Boolean isFeatured;  // Changed from boolean to Boolean (wrapper class)

    private Integer sortOrder;   // Changed from int to Integer (wrapper class)

    private Boolean isActive;    // Changed from boolean to Boolean (wrapper class)

    public Boolean getFeatured() {
        return this.isFeatured;
    }

    public Boolean getActive() {
        return this.isActive;
    }

}
