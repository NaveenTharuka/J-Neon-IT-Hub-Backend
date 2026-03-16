package com.SE.ITHub.service.impl;

import com.SE.ITHub.dto.PortfolioImageRequestDto;
import com.SE.ITHub.dto.PortfolioImageResponseDto;
import com.SE.ITHub.mapper.PortfolioImageMapper;
import com.SE.ITHub.model.PortfolioImage;
import com.SE.ITHub.model.PortfolioItem;
import com.SE.ITHub.repository.PortfolioImageRepository;
import com.SE.ITHub.repository.PortfolioItemRepository;
import com.SE.ITHub.service.PortfolioImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PortfolioImageServiceImpl implements PortfolioImageService {

    private final PortfolioImageRepository portfolioImageRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    public PortfolioImageServiceImpl(
            PortfolioImageRepository portfolioImageRepository,
            PortfolioItemRepository portfolioItemRepository
    ) {
        this.portfolioImageRepository = portfolioImageRepository;
        this.portfolioItemRepository = portfolioItemRepository;
    }

    @Override
    public PortfolioImageResponseDto addImageToPortfolioItem(UUID portfolioItemId, PortfolioImageRequestDto request) {
        if (request.getImageUrl() == null || request.getImageUrl().isBlank()) {
            throw new RuntimeException("Image URL is required");
        }

        PortfolioItem portfolioItem = portfolioItemRepository.findById(portfolioItemId)
                .orElseThrow(() -> new RuntimeException("Portfolio item not found"));

        if (Boolean.TRUE.equals(request.getIsCover())) {
            clearExistingCoverImage(portfolioItemId);
        }

        PortfolioImage image = PortfolioImageMapper.toEntity(request, portfolioItem);
        PortfolioImage saved = portfolioImageRepository.save(image);

        return PortfolioImageMapper.toResponseDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PortfolioImageResponseDto> getImagesByPortfolioItemId(UUID portfolioItemId) {
        portfolioItemRepository.findById(portfolioItemId)
                .orElseThrow(() -> new RuntimeException("Portfolio item not found"));

        return portfolioImageRepository.findByPortfolioItemIdOrderBySortOrderAsc(portfolioItemId)
                .stream()
                .map(PortfolioImageMapper::toResponseDto)
                .toList();
    }

    @Override
    public PortfolioImageResponseDto updatePortfolioImage(UUID imageId, PortfolioImageRequestDto request) {
        PortfolioImage image = portfolioImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Portfolio image not found"));

        if (request.getImageUrl() == null || request.getImageUrl().isBlank()) {
            throw new RuntimeException("Image URL is required");
        }

        if (Boolean.TRUE.equals(request.getIsCover())) {
            clearExistingCoverImage(image.getPortfolioItem().getId());
        }

        PortfolioImageMapper.updateEntityFromRequest(image, request);
        PortfolioImage updated = portfolioImageRepository.save(image);

        return PortfolioImageMapper.toResponseDto(updated);
    }

    @Override
    public void deletePortfolioImage(UUID imageId) {
        PortfolioImage image = portfolioImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Portfolio image not found"));

        portfolioImageRepository.delete(image);
    }

    private void clearExistingCoverImage(UUID portfolioItemId) {
        List<PortfolioImage> images = portfolioImageRepository.findByPortfolioItemIdOrderBySortOrderAsc(portfolioItemId);

        for (PortfolioImage image : images) {
            if (Boolean.TRUE.equals(image.getIsCover())) {
                image.setIsCover(false);
            }
        }

        portfolioImageRepository.saveAll(images);
    }
}