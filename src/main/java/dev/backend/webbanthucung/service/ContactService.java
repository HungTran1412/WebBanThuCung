package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.request.ContactRequest;
import dev.backend.webbanthucung.entity.Contact;

import java.util.List;

public interface ContactService {
    Contact saveContact(ContactRequest request);
    List<Contact> getAllContact();
}
