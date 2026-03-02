package com.SE.ITHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceUpdateDto {

    private String title;
    private String icon;
    private String slug;
    private String shortDescription;
    private int sortOrder;
    private boolean isActive;

}
