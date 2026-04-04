package com.SE.ITHub.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class MediaResponseDto {
    private UUID id;
    private String fileName;
    private String fileUrl;
    private String mimeType;
    private Long fileSizeBytes;
    private Integer width;
    private Integer height;
    private String altText;
    private String caption;
    private LocalDateTime uploadedAt;
}