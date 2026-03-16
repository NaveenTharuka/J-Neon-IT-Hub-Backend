package com.SE.ITHub.repository;

import com.SE.ITHub.model.PortfolioImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PortfolioImageRepository extends JpaRepository<PortfolioImage, UUID> {

    List<PortfolioImage> findByPortfolioItemIdOrderBySortOrderAsc(UUID portfolioItemId);
}