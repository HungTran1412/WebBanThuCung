package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.model.AdminDTO;
import dev.backend.webbanthucung.dto.request.AdminLoginRequest;
import dev.backend.webbanthucung.dto.request.ChangePasswordRequest;
import dev.backend.webbanthucung.dto.respone.LoginRespone;
import dev.backend.webbanthucung.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest request) {
        try {
            LoginRespone response = adminService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("code", "fail", "message", e.getMessage()));
        }
    }

    @GetMapping("/getInfor/{id}")
    public AdminDTO getInforById(@PathVariable Long id){
        return adminService.getInforById(id);
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

