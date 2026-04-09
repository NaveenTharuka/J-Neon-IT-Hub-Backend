package com.SE.ITHub.service;

import com.google.analytics.data.v1beta.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoogleAnalyticsServiceImpl {

    @Value("${google.analytics.property-id}")
    private String propertyId;

    private final BetaAnalyticsDataClient analyticsDataClient;

    public GoogleAnalyticsServiceImpl(BetaAnalyticsDataClient analyticsDataClient) {
        this.analyticsDataClient = analyticsDataClient;
    }

    // Overview Stats for Dashboard Cards
    public Map<String, Object> getOverviewStats(String startDate, String endDate) {
        try {
            RunReportRequest request = RunReportRequest.newBuilder()
                    .setProperty("properties/" + propertyId)
                    .addMetrics(Metric.newBuilder().setName("activeUsers"))
                    .addMetrics(Metric.newBuilder().setName("sessions"))
                    .addMetrics(Metric.newBuilder().setName("screenPageViews"))
                    .addMetrics(Metric.newBuilder().setName("averageSessionDuration"))
                    .addDateRanges(DateRange.newBuilder()
                            .setStartDate(startDate)
                            .setEndDate(endDate))
                    .build();

            RunReportResponse response = analyticsDataClient.runReport(request);

            Map<String, Object> stats = new HashMap<>();
            if (response.getRowsCount() > 0) {
                stats.put("totalUsers", response.getRows(0).getMetricValues(0).getValue());
                stats.put("totalSessions", response.getRows(0).getMetricValues(1).getValue());
                stats.put("totalPageViews", response.getRows(0).getMetricValues(2).getValue());
                stats.put("avgSessionDuration", response.getRows(0).getMetricValues(3).getValue());
            } else {
                stats.put("totalUsers", "0");
                stats.put("totalSessions", "0");
                stats.put("totalPageViews", "0");
                stats.put("avgSessionDuration", "0");
            }
            return stats;

        } catch (Exception e) {
            Map<String, Object> errorStats = new HashMap<>();
            errorStats.put("totalUsers", "0");
            errorStats.put("totalSessions", "0");
            errorStats.put("totalPageViews", "0");
            errorStats.put("avgSessionDuration", "0");
            errorStats.put("error", e.getMessage());
            return errorStats;
        }
    }

    // Daily Traffic for Line Chart
    public Map<String, Object> getDailyTraffic(String startDate, String endDate) {
        try {
            RunReportRequest request = RunReportRequest.newBuilder()
                    .setProperty("properties/" + propertyId)
                    .addDimensions(Dimension.newBuilder().setName("date"))
                    .addMetrics(Metric.newBuilder().setName("activeUsers"))
                    .addMetrics(Metric.newBuilder().setName("screenPageViews"))
                    .addDateRanges(DateRange.newBuilder()
                            .setStartDate(startDate)
                            .setEndDate(endDate))
                    .build();

            RunReportResponse response = analyticsDataClient.runReport(request);

            List<String> dates = new ArrayList<>();
            List<String> users = new ArrayList<>();
            List<String> views = new ArrayList<>();

            for (Row row : response.getRowsList()) {
                dates.add(row.getDimensionValues(0).getValue());
                users.add(row.getMetricValues(0).getValue());
                views.add(row.getMetricValues(1).getValue());
            }

            Map<String, Object> result = new HashMap<>();
            result.put("labels", dates);
            result.put("users", users);
            result.put("pageViews", views);
            return result;

        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("labels", new ArrayList<>());
            result.put("users", new ArrayList<>());
            result.put("pageViews", new ArrayList<>());
            return result;
        }
    }

    // Top Services (most viewed service pages)
    public Map<String, Object> getTopServices(String startDate, String endDate) {
        try {
            RunReportRequest request = RunReportRequest.newBuilder()
                    .setProperty("properties/" + propertyId)
                    .addDimensions(Dimension.newBuilder().setName("pagePath"))
                    .addMetrics(Metric.newBuilder().setName("screenPageViews"))
                    .addDateRanges(DateRange.newBuilder()
                            .setStartDate(startDate)
                            .setEndDate(endDate))
                    .setLimit(50)
                    .build();

            RunReportResponse response = analyticsDataClient.runReport(request);

            List<Map<String, String>> services = new ArrayList<>();
            for (Row row : response.getRowsList()) {
                String path = row.getDimensionValues(0).getValue();
                String views = row.getMetricValues(0).getValue();

                // Only include service pages, exclude admin
                if ((path.contains("/services/") || path.contains("/service/"))
                        && !path.contains("/admin/")) {
                    Map<String, String> service = new HashMap<>();
                    service.put("path", path);
                    service.put("views", views);
                    services.add(service);
                }
            }

            // Sort by views descending
            services.sort((a, b) -> Integer.compare(
                    Integer.parseInt(b.get("views")),
                    Integer.parseInt(a.get("views"))
            ));

            Map<String, Object> result = new HashMap<>();
            result.put("services", services.stream().limit(10).collect(Collectors.toList()));
            return result;

        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("services", new ArrayList<>());
            return result;
        }
    }

    // Traffic Sources
    public Map<String, Object> getTrafficSources(String startDate, String endDate) {
        try {
            RunReportRequest request = RunReportRequest.newBuilder()
                    .setProperty("properties/" + propertyId)
                    .addDimensions(Dimension.newBuilder().setName("sessionDefaultChannelGroup"))
                    .addMetrics(Metric.newBuilder().setName("sessions"))
                    .addDateRanges(DateRange.newBuilder()
                            .setStartDate(startDate)
                            .setEndDate(endDate))
                    .build();

            RunReportResponse response = analyticsDataClient.runReport(request);

            List<Map<String, String>> sources = new ArrayList<>();
            for (Row row : response.getRowsList()) {
                Map<String, String> source = new HashMap<>();
                source.put("channel", row.getDimensionValues(0).getValue());
                source.put("sessions", row.getMetricValues(0).getValue());
                sources.add(source);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("sources", sources);
            return result;

        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("sources", new ArrayList<>());
            return result;
        }
    }
}