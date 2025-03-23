package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.request.ContactRequest;
import dev.backend.webbanthucung.entity.Contact;
import dev.backend.webbanthucung.service.ContactService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
@RestController
public class ContactController {
    @Autowired
    ContactService contactService;

    //Gui lien he
    @PostMapping("/contacts")
    public Contact saveContact(@RequestBody ContactRequest request) {
        return contactService.saveContact(request);
    }

    //Lay thong tin tat ca lien he
    @GetMapping("/contacts")
    public List<Contact> getAllContact() {
        return contactService.getAllContact();
    }
}
