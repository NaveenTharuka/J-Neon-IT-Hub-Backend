package com.SE.ITHub.service;

import com.SE.ITHub.dto.PortfolioImageRequestDto;
import com.SE.ITHub.dto.PortfolioImageResponseDto;

import java.util.List;
import java.util.UUID;

public interface PortfolioImageService {
    PortfolioImageResponseDto addImageToPortfolioItem(UUID portfolioItemId, PortfolioImageRequestDto request);
    List<PortfolioImageResponseDto> getImagesByPortfolioItemId(UUID portfolioItemId);
    PortfolioImageResponseDto updatePortfolioImage(UUID imageId, PortfolioImageRequestDto request);
    void deletePortfolioImage(UUID imageId);
}