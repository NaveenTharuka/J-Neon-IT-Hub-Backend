package com.SE.ITHub.service.impl;

import com.SE.ITHub.dto.PortfolioItemRequestDto;
import com.SE.ITHub.dto.PortfolioItemResponseDto;
import com.SE.ITHub.mapper.PortfolioItemMapper;
import com.SE.ITHub.model.PortfolioItem;
import com.SE.ITHub.model.Services;
import com.SE.ITHub.repository.PortfolioItemRepository;
import com.SE.ITHub.service.PortfolioItemService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
@Transactional
public class PortfolioItemServiceImpl implements PortfolioItemService {

    private final PortfolioItemRepository portfolioItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public PortfolioItemServiceImpl(PortfolioItemRepository portfolioItemRepository) {
        this.portfolioItemRepository = portfolioItemRepository;
    }

    @Override
    public PortfolioItemResponseDto createPortfolioItem(PortfolioItemRequestDto request) {

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new RuntimeException("Portfolio item title is required");
        }

        if (portfolioItemRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Slug already exists");
        }

        PortfolioItem portfolioItem = PortfolioItemMapper.toEntity(request);

        if (request.getServiceId() != null) {
            Services serviceRef = entityManager.getReference(Services.class, request.getServiceId());
            portfolioItem.setService(serviceRef);
        }

        PortfolioItem savedItem = portfolioItemRepository.save(portfolioItem);

        return PortfolioItemMapper.toResponseDto(savedItem);
    }

    @Override
    public PortfolioItemResponseDto updatePortfolioItem(UUID id, PortfolioItemRequestDto request) {

        PortfolioItem portfolioItem = portfolioItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio item not found"));

        if (portfolioItemRepository.existsBySlugAndIdNot(request.getSlug(), id)) {
            throw new RuntimeException("Slug already exists");
        }

        PortfolioItemMapper.updateEntityFromRequest(portfolioItem, request);

        if (request.getServiceId() != null) {
            Services serviceRef = entityManager.getReference(Services.class, request.getServiceId());
            portfolioItem.setService(serviceRef);
        } else {
            portfolioItem.setService(null);
        }

        PortfolioItem updatedItem = portfolioItemRepository.save(portfolioItem);

        return PortfolioItemMapper.toResponseDto(updatedItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PortfolioItemResponseDto> getAllPortfolioItems() {

        return portfolioItemRepository.findAll()
                .stream()
                .map(PortfolioItemMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PortfolioItemResponseDto getPortfolioItemById(UUID id) {

        PortfolioItem portfolioItem = portfolioItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio item not found"));

        return PortfolioItemMapper.toResponseDto(portfolioItem);
    }

    @Override
    public void deletePortfolioItem(UUID id) {

        PortfolioItem portfolioItem = portfolioItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Portfolio item not found"));

        portfolioItemRepository.delete(portfolioItem);
    }
}