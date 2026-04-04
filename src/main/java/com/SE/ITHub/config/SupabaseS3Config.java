package com.SE.ITHub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
public class SupabaseS3Config {

    @Bean
    public S3Client supabaseS3Client(
            @Value("${supabase.storage.endpoint}") String endpoint,
            @Value("${supabase.storage.region}") String region,
            @Value("${supabase.storage.accessKey}") String accessKey,
            @Value("${supabase.storage.secretKey}") String secretKey
    ) {
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }
}
