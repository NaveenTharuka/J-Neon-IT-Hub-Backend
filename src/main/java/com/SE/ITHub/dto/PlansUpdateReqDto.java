package com.SE.ITHub.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PlansUpdateReqDto {

    @NotNull
    private UUID planId;

    @NotNull
    private UUID serviceId;

    @NotNull
    @Size(max = 80)
    private String planName;

    private BigDecimal price;

    @Size(max = 30)
    private String priceType;

    @Size(min = 3, max = 3)
    private String currency;

    @Size(max = 20)
    private String billingPeriod;

    @Size(max = 255)
    private String description;

    private String features;

    private Boolean isFeatured;

    private int sortOrder;

    private Boolean isActive;

    public Boolean getFeatured() {
        return this.isFeatured;
    }

    public Boolean getActive() {
        return this.isActive;
    }

}