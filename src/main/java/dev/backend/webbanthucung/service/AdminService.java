package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.model.AdminDTO;
import dev.backend.webbanthucung.dto.respone.LoginRespone;

public interface AdminService {
    LoginRespone login(String email, String password);
    boolean changePassword(String email, String oldPassword, String newPassword);
    AdminDTO getInforById(Long id);
}
