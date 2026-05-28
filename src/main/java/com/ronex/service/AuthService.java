package com.ronex.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ronex.dto.LoginRequest;
import com.ronex.dto.RegisterRequest;
import com.ronex.model.User;
import com.ronex.repository.UserRepository;
import com.ronex.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // 🔥 Simple in-memory OTP store (MVP)
    private final Map<String, String> otpStore = new HashMap<>();

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // ---------- REGISTER ----------
    public String register(RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getEmail()); // important
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return "User Registered Successfully";
    }

    // ---------- LOGIN WITH PASSWORD ----------
    public String login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return jwtUtil.generateToken(request.getEmail());
    }

    // ---------- SEND OTP ----------
    public String sendOtp(String email) {

        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        otpStore.put(email, otp);

        // TEMP: console lo OTP (later Email/SMS)
        System.out.println("OTP for " + email + " = " + otp);

        return "OTP sent successfully";
    }

    // ---------- VERIFY OTP ----------
    public String verifyOtp(String email, String otp) {

        String savedOtp = otpStore.get(email);

        if (savedOtp == null || !savedOtp.equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        otpStore.remove(email);
        return jwtUtil.generateToken(email);
    }
}