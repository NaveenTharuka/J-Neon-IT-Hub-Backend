package com.SE.ITHub.repository;

import com.SE.ITHub.model.PortfolioItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class PortfolioItemRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PortfolioItemRepository portfolioItemRepository;

    private PortfolioItem portfolioItem;
    private UUID testId;

    @BeforeEach
    void setUp() {
        portfolioItem = new PortfolioItem();
        portfolioItem.setTitle("Test Repository Item");
        portfolioItem.setSlug("test-repo-slug");
        portfolioItem.setClientName("Repo Client");
        portfolioItem.setSummary("Repo Summary");
        portfolioItem.setDescription("Repo Description");
        portfolioItem.setProjectDate(LocalDate.now());
        portfolioItem.setProjectUrl("http://repo.com");
        portfolioItem.setIsPublished(true);
        portfolioItem.setSortOrder(5);
        portfolioItem.setCreatedAt(LocalDateTime.now());
        portfolioItem.setUpdatedAt(LocalDateTime.now());

        PortfolioItem saved = entityManager.persistAndFlush(portfolioItem);
        testId = saved.getId();
    }

    @Test
    void findBySlug_ShouldReturnItem() {
        Optional<PortfolioItem> found = portfolioItemRepository.findBySlug("test-repo-slug");

        assertTrue(found.isPresent());
        assertEquals("Test Repository Item", found.get().getTitle());
        assertEquals("test-repo-slug", found.get().getSlug());
    }

    @Test
    void findBySlug_WithNonExistentSlug_ShouldReturnEmpty() {
        Optional<PortfolioItem> found = portfolioItemRepository.findBySlug("non-existent-slug");

        assertFalse(found.isPresent());
    }

    @Test
    void existsBySlug_ShouldReturnTrue() {
        boolean exists = portfolioItemRepository.existsBySlug("test-repo-slug");

        assertTrue(exists);
    }

    @Test
    void existsBySlug_WithNonExistentSlug_ShouldReturnFalse() {
        boolean exists = portfolioItemRepository.existsBySlug("non-existent-slug");

        assertFalse(exists);
    }

    @Test
    void existsBySlugAndIdNot_WithDifferentId_ShouldReturnTrue() {
        UUID differentId = UUID.randomUUID();
        boolean exists = portfolioItemRepository.existsBySlugAndIdNot("test-repo-slug", differentId);

        assertTrue(exists);
    }

    @Test
    void existsBySlugAndIdNot_WithSameId_ShouldReturnFalse() {
        boolean exists = portfolioItemRepository.existsBySlugAndIdNot("test-repo-slug", testId);

        assertFalse(exists);
    }
}