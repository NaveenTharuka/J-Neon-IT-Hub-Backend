package com.SE.ITHub.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder({
        "id",
        "name",
        "email",
        "message"
})
@Data
public class ContactResponse {

    private UUID id;
    private String name;
    private String email;
    private String message;
}