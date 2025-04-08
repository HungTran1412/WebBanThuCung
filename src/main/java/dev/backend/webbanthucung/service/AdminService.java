package dev.backend.webbanthucung.service;

import dev.backend.webbanthucung.dto.request.AdminLoginRequest;
import dev.backend.webbanthucung.entity.Admin;
import dev.backend.webbanthucung.repository.AdminRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.rmi.server.LogStream.log;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    //ham dang nhap
    public boolean login(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);

        if (admin == null) {
            return false;
        }

        passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(password, admin.getPassword());
    }

    //ham doi mat khau
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        Admin admin = adminRepository.findByEmail(email);

        if (admin == null || admin.getPassword() == null) {
            throw new IllegalArgumentException("Không tìm thấy email hoặc mật khẩu bị trống!");
        }

        // Kiểm tra mật khẩu cũ trước khi đổi
        if (!passwordEncoder.matches(oldPassword, admin.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu cũ không đúng!");
        }

        // Mã hóa mật khẩu mới và cập nhật
        admin.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);
        return true;
    }
}
