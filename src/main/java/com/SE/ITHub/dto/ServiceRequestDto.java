package com.SE.ITHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDto {

    private String title;

    private String shortDescription;

    private String slug;

    private String icon;

    private boolean isActive;

}
