package com.manareels.service;

import com.manareels.model.User;
import com.manareels.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public String uploadProfilePhoto(Long userId, MultipartFile file) {

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String uploadDir = "uploads/";

            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File saveFile = new File(uploadDir + fileName);
            file.transferTo(saveFile);

            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                user.setProfilePic(fileName);
                userRepository.save(user);
            }

            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}