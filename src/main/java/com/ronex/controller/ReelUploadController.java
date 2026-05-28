package com.ronex.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/reels")
@CrossOrigin(origins = "*")
public class ReelUploadController {

    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping(
        value = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> upload(
            @RequestParam("video") MultipartFile video,
            @RequestParam("caption") String caption,
            @RequestParam("userId") Long userId
    ) {
        try {
            // ✅ FORCE .mp4 extension
            String fileName = System.currentTimeMillis() + ".mp4";

            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(video.getInputStream(), path);

            String videoUrl = "http://localhost:8080/uploads/" + fileName;

            return ResponseEntity.ok(videoUrl);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed");
        }
    }
}