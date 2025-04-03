package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.request.AdminLoginRequest;
import dev.backend.webbanthucung.entity.Admin;
import dev.backend.webbanthucung.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.rmi.server.LogStream.log;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public boolean login(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);

        if (admin == null) {
            return false;
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(password, admin.getPassword());
    }
}
