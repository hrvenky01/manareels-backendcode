package com.ronex.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ronex.model.Reel;
import com.ronex.model.User;
import com.ronex.repository.ReelRepository;
import com.ronex.repository.UserRepository;

import java.io.File;
import java.util.List;

@Service
public class ReelService {

    private final ReelRepository reelRepository;
    private final UserRepository userRepository;

    public ReelService(ReelRepository reelRepository,
                       UserRepository userRepository) {
        this.reelRepository = reelRepository;
        this.userRepository = userRepository;
    }

    /* ---------------- UPLOAD REEL (FINAL FIXED) ---------------- */
    public Reel uploadReel(User user, MultipartFile file, String caption) {

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            String folderPath = "uploads/";
            File dir = new File(folderPath);
            if (!dir.exists()) dir.mkdirs();

            String filePath = folderPath + fileName;

            file.transferTo(new File(filePath));

            Reel reel = new Reel();
            reel.setUser(user);
            reel.setCaption(caption);

            // 🔥 IMPORTANT FIX (MOBILE ACCESSIBLE URL)
            reel.setVideoUrl("http://10.0.2.2:8080/uploads/" + fileName);

            reel.setLikeCount(0);

            return reelRepository.save(reel);

        } catch (Exception e) {
            throw new RuntimeException("Reel upload failed: " + e.getMessage());
        }
    }

    /* ---------------- LIKE REEL ---------------- */
    public String likeReel(Long id) {

        Reel reel = reelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reel not found"));

        reel.setLikeCount(reel.getLikeCount() + 1);
        reelRepository.save(reel);

        return "Liked reel " + id;
    }

    /* ---------------- COMMENT REEL ---------------- */
    public String commentReel(Long id, String comment) {
        return "Comment added to reel " + id + ": " + comment;
    }

    /* ---------------- FEED ---------------- */
    public List<Reel> getAllReels() {
        return reelRepository.findAll();
    }

    /* ---------------- SEARCH ---------------- */
    public List<Reel> searchReels(String query) {
        return reelRepository.findByCaptionContainingIgnoreCase(query);
    }

    /* ---------------- EXPLORE ---------------- */
    public List<Reel> getExploreReels() {
        return reelRepository.getExploreReels();
    }
}