package com.SE.ITHub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "service_plans")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ServicePlans {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Services service;

    @NotNull
    @Size(max = 80)
    @Column(name = "plan_name", nullable = false, length = 80)
    private String planName;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Size(max = 30)
    @Column(name = "price_type", length = 30)
    private String priceType;

    @Size(min = 3, max = 3)
    @Column(length = 3)
    private String currency;

    @Size(max = 20)
    @Column(name = "billing_period", length = 20)
    private String billingPeriod;

    @Size(max = 255)
    @Column(length = 255)
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String features;

    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setFeatured(boolean featured) {
        this.isFeatured = featured;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public Boolean getFeatured() {
        return this.isFeatured;
    }

    public Boolean getActive() {
        return this.isActive;
    }
}