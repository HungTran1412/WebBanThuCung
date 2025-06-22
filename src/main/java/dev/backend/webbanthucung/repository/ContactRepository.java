package dev.backend.webbanthucung.repository;

import dev.backend.webbanthucung.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository <Contact, String> {

    @Query("SELECT c.id FROM Contact c WHERE c.id LIKE CONCAT('CT', ?1, '%') ORDER BY c.id DESC LIMIT 1")
    String findLastContactId(String datePrefix);//tien to ngay thang
}
