package com.SE.ITHub.controller;

import com.SE.ITHub.dto.PortfolioImageRequestDto;
import com.SE.ITHub.dto.PortfolioImageResponseDto;
import com.SE.ITHub.service.PortfolioImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/portfolio-items/{portfolioItemId}/images")
public class PortfolioImageController {

    private final PortfolioImageService portfolioImageService;

    public PortfolioImageController(PortfolioImageService portfolioImageService) {
        this.portfolioImageService = portfolioImageService;
    }

    @PostMapping
    public ResponseEntity<PortfolioImageResponseDto> addImage(
            @PathVariable UUID portfolioItemId,
            @RequestBody PortfolioImageRequestDto request
    ) {
        return ResponseEntity.ok(
                portfolioImageService.addImageToPortfolioItem(portfolioItemId, request)
        );
    }

    @GetMapping
    public ResponseEntity<List<PortfolioImageResponseDto>> getImages(
            @PathVariable UUID portfolioItemId
    ) {
        return ResponseEntity.ok(
                portfolioImageService.getImagesByPortfolioItemId(portfolioItemId)
        );
    }

    @PutMapping("/{imageId}")
    public ResponseEntity<PortfolioImageResponseDto> updateImage(
            @PathVariable UUID imageId,
            @RequestBody PortfolioImageRequestDto request
    ) {
        return ResponseEntity.ok(
                portfolioImageService.updatePortfolioImage(imageId, request)
        );
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID imageId) {
        portfolioImageService.deletePortfolioImage(imageId);
        return ResponseEntity.noContent().build();
    }
}