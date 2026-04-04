package com.SE.ITHub.controller;

import com.SE.ITHub.dto.PortfolioItemRequestDto;
import com.SE.ITHub.dto.PortfolioItemResponseDto;
import com.SE.ITHub.service.PortfolioItemService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portfolio-items")
public class PortfolioItemController {

    private final PortfolioItemService portfolioItemService;

    public PortfolioItemController(PortfolioItemService portfolioItemService) {
        this.portfolioItemService = portfolioItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<PortfolioItemResponseDto> createPortfolioItem(
            @RequestBody PortfolioItemRequestDto request
    ) {
        PortfolioItemResponseDto response = portfolioItemService.createPortfolioItem(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PortfolioItemResponseDto>> getAllPortfolioItems() {
        List<PortfolioItemResponseDto> items = portfolioItemService.getAllPortfolioItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioItemResponseDto> getPortfolioItemById(
            @PathVariable UUID id
    ) {
        PortfolioItemResponseDto item = portfolioItemService.getPortfolioItemById(id);
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortfolioItemResponseDto> updatePortfolioItem(
            @PathVariable UUID id,
            @RequestBody PortfolioItemRequestDto request
    ) {
        PortfolioItemResponseDto updatedItem = portfolioItemService.updatePortfolioItem(id, request);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolioItem(
            @PathVariable UUID id
    ) {
        portfolioItemService.deletePortfolioItem(id);
        return ResponseEntity.noContent().build();
    }
}