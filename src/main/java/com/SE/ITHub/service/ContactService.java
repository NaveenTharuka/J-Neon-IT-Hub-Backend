package com.SE.ITHub.service;

import com.SE.ITHub.dto.ContactCreateRequest;
import com.SE.ITHub.dto.ContactResponse;
import com.SE.ITHub.model.Contact;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ContactService {
    Contact findById(UUID id);
    ContactResponse addContactMessage(ContactCreateRequest contact);
}
