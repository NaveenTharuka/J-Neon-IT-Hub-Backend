package com.SE.ITHub.mapper;

import com.SE.ITHub.dto.PortfolioItemRequestDto;
import com.SE.ITHub.dto.PortfolioItemResponseDto;
import com.SE.ITHub.model.PortfolioItem;
import com.SE.ITHub.model.Services;

public class PortfolioItemMapper {

    public static PortfolioItem toEntity(PortfolioItemRequestDto request) {

        PortfolioItem item = new PortfolioItem();

        item.setTitle(request.getTitle());
        item.setSlug(request.getSlug());
        item.setClientName(request.getClientName());
        item.setSummary(request.getSummary());
        item.setDescription(request.getDescription());
        item.setProjectDate(request.getProjectDate());
        item.setProjectUrl(request.getProjectUrl());
        item.setIsPublished(request.getIsPublished());
        item.setSortOrder(request.getSortOrder());

        return item;
    }

    public static PortfolioItemResponseDto toResponseDto(PortfolioItem item) {

        return new PortfolioItemResponseDto(
                item.getId(),
                item.getTitle(),
                item.getSlug(),
                item.getClientName(),
                item.getSummary(),
                item.getDescription(),
                item.getService() != null ? item.getService().getId() : null,
                item.getProjectDate(),
                item.getProjectUrl(),
                item.getIsPublished(),
                item.getSortOrder(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }

    public static void updateEntityFromRequest(PortfolioItem item, PortfolioItemRequestDto request) {

        item.setTitle(request.getTitle());
        item.setSlug(request.getSlug());
        item.setClientName(request.getClientName());
        item.setSummary(request.getSummary());
        item.setDescription(request.getDescription());
        item.setProjectDate(request.getProjectDate());
        item.setProjectUrl(request.getProjectUrl());
        item.setIsPublished(request.getIsPublished());
        item.setSortOrder(request.getSortOrder());
    }
}