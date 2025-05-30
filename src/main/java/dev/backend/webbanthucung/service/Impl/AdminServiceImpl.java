package dev.backend.webbanthucung.service.Impl;

import dev.backend.webbanthucung.dto.model.AdminDTO;
import dev.backend.webbanthucung.dto.respone.LoginRespone;
import dev.backend.webbanthucung.entity.Admin;
import dev.backend.webbanthucung.repository.AdminRepository;
import dev.backend.webbanthucung.service.AdminService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    //ham dang nhap
    public LoginRespone login(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);

        if(admin == null){
            throw new RuntimeException("EMAIL IS NOT EXISTS");
        }

        passwordEncoder = new BCryptPasswordEncoder(10);
        if(passwordEncoder.matches(password,admin.getPassword())){
            return new LoginRespone("success", admin.getId());
        }

        throw new RuntimeException("INCORRECT PASSWORD!");
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

    public AdminDTO getInforById(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID IS NOT EXIST:" + id));

        return new AdminDTO(admin.getId(),admin.getEmail(), admin.getImg(), admin.getFullname());
    }
}
