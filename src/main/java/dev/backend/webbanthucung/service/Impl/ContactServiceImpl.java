package dev.backend.webbanthucung.service.Impl;

import dev.backend.webbanthucung.dto.request.ContactRequest;
import dev.backend.webbanthucung.entities.Contact;
import dev.backend.webbanthucung.repository.ContactRepository;
import dev.backend.webbanthucung.service.ContactService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    public String generateContactId() {
        //Lay ngay hien tai
        String datePrefix = LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy"));

        //Tim so thu tu lon nhat trong ngay
        String lastContactId = contactRepository.findLastContactId(datePrefix);

        int nextNumber = 1;
        if (lastContactId != null) {
            //Tach so thu tu tu id cuoi cung
            String lastNumberStr = lastContactId.substring(lastContactId.lastIndexOf('-') + 1);
            nextNumber = Integer.parseInt(lastNumberStr) + 1;
        }

        //Tao id moi
        return String.format("CT%s-%03d", datePrefix, nextNumber);
    }

    //ham luu noi dung lien he
    public Contact saveContact(ContactRequest request) {
        Contact contact = new Contact();

        try {
            //set cac noi dung vao doi tuong
            contact.setId(generateContactId());
            contact.setFullName(request.getFullName());
            contact.setEmail(request.getEmail());
            contact.setPhone(request.getPhone());
            contact.setContent(request.getContent());
            contact.setDate(LocalDate.now());
            contact.setAddress(request.getAddress());

            //luu doi tuong vao database
            return contactRepository.save(contact);
        } catch (Exception e) {
            System.out.println("Loi! khong gui duoc lien he");
            return null;
        }
    }

    //ham lay tat ca lien he
    public List<Contact> getAllContact() {
        return contactRepository.findAll();
    }
}
