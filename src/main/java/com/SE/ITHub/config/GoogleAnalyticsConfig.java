package com.SE.ITHub.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.analytics.data.v1beta.BetaAnalyticsDataClient;
import com.google.analytics.data.v1beta.BetaAnalyticsDataSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class GoogleAnalyticsConfig {

    @Value("${GOOGLE_ANALYTICS_CREDENTIALS:}")
    private String credentialsJson;

    @Bean
    public BetaAnalyticsDataClient betaAnalyticsDataClient() throws IOException {
        GoogleCredentials credentials;

        // Check if credentials are in environment variable (Render.com)
        if (credentialsJson != null && !credentialsJson.isEmpty()) {
            // Use credentials from environment variable
            InputStream credentialsStream = new ByteArrayInputStream(
                    credentialsJson.getBytes(StandardCharsets.UTF_8)
            );
            credentials = GoogleCredentials.fromStream(credentialsStream);
        } else {
            // Fallback for local development - file not in git
            // Place google-analytics-key.json in src/main/resources/ but add to .gitignore
            try {
                credentials = GoogleCredentials.fromStream(
                        new ClassPathResource("google-analytics-key.json").getInputStream()
                );
            } catch (Exception e) {
                throw new RuntimeException(
                        "Google Analytics credentials not found. " +
                                "Set GOOGLE_ANALYTICS_CREDENTIALS environment variable or " +
                                "add google-analytics-key.json to src/main/resources/ (and ensure it's in .gitignore)"
                );
            }
        }

        BetaAnalyticsDataSettings settings = BetaAnalyticsDataSettings.newBuilder()
                .setCredentialsProvider(() -> credentials)
                .build();

        return BetaAnalyticsDataClient.create(settings);
    }
}