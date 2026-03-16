package com.SE.ITHub.mapper;

import com.SE.ITHub.dto.PortfolioImageRequestDto;
import com.SE.ITHub.dto.PortfolioImageResponseDto;
import com.SE.ITHub.model.PortfolioImage;
import com.SE.ITHub.model.PortfolioItem;

public class PortfolioImageMapper {

    public static PortfolioImage toEntity(PortfolioImageRequestDto request, PortfolioItem portfolioItem) {
        PortfolioImage image = new PortfolioImage();
        image.setPortfolioItem(portfolioItem);
        image.setImageUrl(request.getImageUrl());
        image.setIsCover(request.getIsCover());
        image.setSortOrder(request.getSortOrder());
        image.setAltTextOverride(request.getAltTextOverride());
        return image;
    }

    public static PortfolioImageResponseDto toResponseDto(PortfolioImage image) {
        return new PortfolioImageResponseDto(
                image.getId(),
                image.getPortfolioItem().getId(),
                image.getImageUrl(),
                image.getIsCover(),
                image.getSortOrder(),
                image.getAltTextOverride(),
                image.getCreatedAt()
        );
    }

    public static void updateEntityFromRequest(PortfolioImage image, PortfolioImageRequestDto request) {
        image.setImageUrl(request.getImageUrl());
        image.setIsCover(request.getIsCover());
        image.setSortOrder(request.getSortOrder());
        image.setAltTextOverride(request.getAltTextOverride());
    }
}