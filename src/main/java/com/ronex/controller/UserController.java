package com.ronex.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ronex.model.User;
import com.ronex.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* ---------------- GET LOGGED-IN USER PROFILE ---------------- */
    @GetMapping("/me")
    public ResponseEntity<User> getMyProfile() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    /* ---------------- UPLOAD PROFILE PHOTO (JWT USER) ---------------- */
    @PostMapping("/profile/photo")
    public ResponseEntity<?> uploadProfilePhoto(
            @RequestParam("file") MultipartFile file
    ) {
        String imageUrl = userService.uploadProfilePhoto(file);
        return ResponseEntity.ok(imageUrl);
    }
}