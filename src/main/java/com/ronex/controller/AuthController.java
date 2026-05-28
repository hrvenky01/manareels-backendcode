package com.ronex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ronex.dto.LoginRequest;
import com.ronex.dto.LoginResponse;
import com.ronex.dto.RegisterRequest;
import com.ronex.dto.OtpRequest;
import com.ronex.dto.OtpVerifyRequest;
import com.ronex.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ---------- REGISTER ----------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // ---------- LOGIN WITH PASSWORD ----------
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        String token = authService.login(request);

        return ResponseEntity.ok(
                new LoginResponse(token)
        );
    }

    // ---------- SEND OTP ----------
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody OtpRequest request) {
        return ResponseEntity.ok(authService.sendOtp(request.getEmail()));
    }

    // ---------- VERIFY OTP ----------
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerifyRequest request) {
        return ResponseEntity.ok(
                authService.verifyOtp(request.getEmail(), request.getOtp())
        );
    }
}