package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.request.AdminLoginRequest;
import dev.backend.webbanthucung.dto.request.ChangePasswordRequest;
import dev.backend.webbanthucung.entity.Admin;
import dev.backend.webbanthucung.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;


//    @PostMapping("/login")
//    public boolean login(@RequestBody AdminLoginRequest request) {
//        return adminService.login(request.getEmail(), request.getPassword());
//    }

    @PostMapping("/login")
    public Map<String, Boolean>  login(@RequestBody AdminLoginRequest request){
        Map<String, Boolean> map = new HashMap<>();
        map.put("code", adminService.login(request.getEmail(), request.getPassword()));
        return map;
    }

    @PutMapping("/change-pass")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        boolean success = adminService.changePassword(request.getEmail(), request.getOldPassword(), request.getNewPassword());

        if (success) {
            return ResponseEntity.ok("Đổi mật khẩu thành công!");
        } else {
            return ResponseEntity.status(400).body("Mật khẩu cũ không đúng hoặc email không tồn tại.");
        }
    }

//    public ResponseEntity<AuthenticationRespone> login(@RequestBody AdminLoginRequest request) {
//        boolean isAuthenticated = adminService.login(request.getEmail(), request.getPassword());
//    }
}

