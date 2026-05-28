package com.ronex.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import com.ronex.dto.ProfileResponse;
import com.ronex.security.JwtUtil;
import com.ronex.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService service;
    private final JwtUtil jwtUtil;

    public ProfileController(ProfileService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ProfileResponse getProfile(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        // 🔐 SAFETY CHECK
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractUsername(token);

        return service.getProfile(email);
    }
}