package com.SE.ITHub.service;

import com.SE.ITHub.dto.PortfolioItemRequestDto;
import com.SE.ITHub.dto.PortfolioItemResponseDto;

import java.util.List;
import java.util.UUID;

public interface PortfolioItemService {
    PortfolioItemResponseDto createPortfolioItem(PortfolioItemRequestDto request);
    PortfolioItemResponseDto updatePortfolioItem(UUID id, PortfolioItemRequestDto request);
    List<PortfolioItemResponseDto> getAllPortfolioItems();
    PortfolioItemResponseDto getPortfolioItemById(UUID id);
    void deletePortfolioItem(UUID id);
}