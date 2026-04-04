package com.SE.ITHub.service;

import com.SE.ITHub.dto.ContactCreateRequest;
import com.SE.ITHub.dto.ContactResponse;
import com.SE.ITHub.dto.ContactTagRequest;
import com.SE.ITHub.model.Contact;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

@Service
public interface ContactService {
    Contact findById(UUID id);
    ContactResponse addContactMessage(ContactCreateRequest contact);
    List<Contact> findAll();
    String deleteContact(UUID id);
    ContactResponse updateContactMessage(ContactTagRequest contactRequest);
}
