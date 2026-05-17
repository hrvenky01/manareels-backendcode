package com.manareels.controller;

import com.manareels.model.User;
import com.manareels.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* ---------------- GET USER PROFILE ---------------- */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    /* ---------------- UPLOAD PROFILE PHOTO ---------------- */
    @PostMapping("/{userId}/upload-photo")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {
        String result = userService.uploadProfilePhoto(userId, file);
        return ResponseEntity.ok(result);
    }
}