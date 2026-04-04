package com.SE.ITHub.mapper;

import com.SE.ITHub.dto.ContactCreateRequest;
import com.SE.ITHub.dto.ContactResponse;
import com.SE.ITHub.dto.ContactTagRequest;
import com.SE.ITHub.model.Contact;
import com.SE.ITHub.service.ContactService;
import com.SE.ITHub.service.impl.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.SE.ITHub.model.Contact;

public class ContactMapper {

    private ContactMapper() {
    }

    public static Contact toEntity(ContactCreateRequest dto) {
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setEmail(dto.getEmail());
        contact.setMessage(dto.getMessage());
        contact.setStatus("new");
        return contact;
    }

    public static ContactResponse toResponse(Contact contact) {
        ContactResponse res = new ContactResponse();
        res.setId(contact.getId());
        res.setName(contact.getName());
        res.setEmail(contact.getEmail());
        res.setMessage(contact.getMessage());
        return res;
    }
}