package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.entity.Admin;
import dev.backend.webbanthucung.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public boolean login(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        boolean result;
        if (admin != null && admin.getPassword().equals(password)) {
            return true;// Đăng nhập thành công
        } else {
            return false;
        }
    }
}
