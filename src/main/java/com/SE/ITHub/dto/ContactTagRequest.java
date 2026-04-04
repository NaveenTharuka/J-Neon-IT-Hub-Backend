package com.SE.ITHub.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ContactTagRequest {
    private UUID id;
    private  String status;
}