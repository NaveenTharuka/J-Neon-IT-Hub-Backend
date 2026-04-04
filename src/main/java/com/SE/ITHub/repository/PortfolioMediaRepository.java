package com.SE.ITHub.repository;

import com.SE.ITHub.model.PortfolioMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PortfolioMediaRepository extends JpaRepository<PortfolioMedia, UUID> {
}
