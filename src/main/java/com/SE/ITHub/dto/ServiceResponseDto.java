package com.SE.ITHub.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ServiceResponseDto {

    private UUID id;
    private String title;
    private String icon;
    private String slug;
    private String shortDescription;
    private int sortOrder;
}
