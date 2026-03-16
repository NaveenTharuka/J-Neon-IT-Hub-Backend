package com.SE.ITHub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "portfolio_items")
@Data
public class PortfolioItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String slug;
    private String clientName;
    private String summary;
    private String description;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services service;
    private LocalDate projectDate;
    private String projectUrl;
    private Boolean isPublished;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "portfolioItem", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<PortfolioImage> images = new ArrayList<>();
}
