package com.SE.ITHub.controller;

import com.SE.ITHub.service.GoogleAnalyticsServiceImpl;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "http://localhost:5174")
public class AnalyticsController {

    private final GoogleAnalyticsServiceImpl analyticsService;

    public AnalyticsController(GoogleAnalyticsServiceImpl analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/overview")
    public Map<String, Object> getOverview(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return analyticsService.getOverviewStats(startDate, endDate);
    }

    @GetMapping("/daily-traffic")
    public Map<String, Object> getDailyTraffic(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return analyticsService.getDailyTraffic(startDate, endDate);
    }

    @GetMapping("/top-services")
    public Map<String, Object> getTopServices(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return analyticsService.getTopServices(startDate, endDate);
    }

    @GetMapping("/traffic-sources")
    public Map<String, Object> getTrafficSources(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return analyticsService.getTrafficSources(startDate, endDate);
    }
}