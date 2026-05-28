package com.ronex.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ronex.model.User;
import com.ronex.repository.UserRepository;
import com.ronex.utils.SecurityUtils;

import java.io.File;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* ---------------- GET LOGGED-IN USER ---------------- */
    public User getCurrentUser() {

        String email = SecurityUtils.getCurrentUsername();

        if (email == null) {
            throw new RuntimeException("Unauthorized");
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /* ---------------- UPLOAD PROFILE PHOTO (FINAL) ---------------- */
    public String uploadProfilePhoto(MultipartFile file) {

        try {
            // 🔥 FILE NAME
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // 🔥 STORAGE
            String uploadDir = "uploads/profile/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            File saveFile = new File(uploadDir + fileName);
            file.transferTo(saveFile);

            // 🔐 JWT USER
            User user = getCurrentUser();

            // 🔥 SAVE FULL URL
            String imageUrl = "http://localhost:8080/uploads/profile/" + fileName;
            user.setProfilePic(imageUrl);

            userRepository.save(user);

            return imageUrl;

        } catch (Exception e) {
            throw new RuntimeException("Profile photo upload failed");
        }
    }
}