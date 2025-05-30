package dev.backend.webbanthucung.service;

public interface EmailService {
    void sendMail(String to, String subject, String body);
}
