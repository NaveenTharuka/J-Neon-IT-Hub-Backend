package com.SE.ITHub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "portfolio_images")
@Data
public class PortfolioMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    @ManyToOne
    @JoinColumn(name = "portfolio_item_id")
    private PortfolioItem portfolioItem;

//    private Integer sortOrder;
//    private String altTextOverride;
//    private LocalDateTime createdAt;

}