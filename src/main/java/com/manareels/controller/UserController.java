package com.manareels.controller;

import com.manareels.model.User;
import com.manareels.service.UserService;
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

    // GET USER PROFILE
    @GetMapping("/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    // UPLOAD PROFILE PHOTO
    @PostMapping("/{userId}/upload-photo")
    public String uploadPhoto(@PathVariable Long userId,
                              @RequestParam("file") MultipartFile file) {
        return userService.uploadProfilePhoto(userId, file);
    }
}