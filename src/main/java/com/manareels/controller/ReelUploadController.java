package com.manareels.controller;

import com.manareels.model.Reel;
import com.manareels.repository.ReelRepository;
import com.manareels.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;

@RestController
@RequestMapping("/reels")
public class ReelUploadController {

    @Autowired
    private ReelRepository reelRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam("video") MultipartFile file,
            @RequestParam("caption") String caption,
            @RequestParam("userId") Long userId
    ) {

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path path = Paths.get("uploads/" + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path);

            Reel reel = new Reel();
            reel.setVideoUrl("/uploads/" + fileName);
            reel.setCaption(caption);
            reel.setUser(userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found")));

            return ResponseEntity.ok(reelRepository.save(reel));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed");
        }
    }
}