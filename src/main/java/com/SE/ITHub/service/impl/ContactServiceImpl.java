package com.SE.ITHub.service.impl;

import com.SE.ITHub.dto.ContactCreateRequest;
import com.SE.ITHub.dto.ContactResponse;
import com.SE.ITHub.dto.ContactTagRequest;
import com.SE.ITHub.exception.ContactNotFoundException;
import com.SE.ITHub.mapper.ContactMapper;
import com.SE.ITHub.model.Contact;
import com.SE.ITHub.repository.ContactRepository;
import com.SE.ITHub.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact findById(UUID id){
        return contactRepository.findById(id).orElseThrow(()->new ContactNotFoundException("Contact not found "+id));
    }

    public ContactResponse addContactMessage(ContactCreateRequest contact){
        return  ContactMapper.toResponse(contactRepository.save(ContactMapper.toEntity(contact)));
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public ContactResponse updateContactMessage(ContactTagRequest contactRequest) {
        Contact existingContact =findById(contactRequest.getId());
        existingContact.setStatus(contactRequest.getStatus());
        Contact updatedContact = contactRepository.save(existingContact);
        return ContactMapper.toResponse(updatedContact);
    }

    public String deleteContact(UUID id){
        Contact contact=findById(id);

        contactRepository.delete(contact);
        return "Contact deleted";
    }
}
