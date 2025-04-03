package dev.backend.webbanthucung.controller;

import dev.backend.webbanthucung.dto.request.AdminLoginRequest;
import dev.backend.webbanthucung.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000") // Cho phép React truy cập
public class AdminController {

    @Autowired
    private AdminService adminService;


    @PostMapping("/login")
    public boolean login(@RequestBody AdminLoginRequest request) {
        return adminService.login(request.getEmail(), request.getPassword());
    }



//    public ResponseEntity<AuthenticationRespone> login(@RequestBody AdminLoginRequest request) {
//        boolean isAuthenticated = adminService.login(request.getEmail(), request.getPassword());
//    }
}

