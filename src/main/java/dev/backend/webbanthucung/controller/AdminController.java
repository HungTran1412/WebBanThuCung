package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.request.AdminLoginRequest;
import dev.backend.webbanthucung.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000") // Cho phép React truy cập
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public boolean login(@RequestBody AdminLoginRequest request) {
        if (adminService.login(request.getEmail(), request.getPassword())) {
            ResponseEntity.ok("Đăng nhập thành công!");
            return true;
        } else {
            ResponseEntity.status(401).body("Tài khoản hoặc mật khẩu không hợp lệ");
            return false;
        }
    }
}

