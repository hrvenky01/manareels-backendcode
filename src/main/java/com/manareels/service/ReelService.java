package com.manareels.service;

import com.manareels.model.Reel;
import com.manareels.model.User;
import com.manareels.repository.ReelRepository;
import com.manareels.repository.UserRepository;
import org.springframework.stereotype.Service;

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

    /* ---------------- CREATE REEL ---------------- */
    public Reel createReel(Long userId, Reel reel) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        reel.setUser(user);
        reel.setLikeCount(0);

        return reelRepository.save(reel);
    }

    /* ---------------- UPLOAD (for Controller compatibility) ---------------- */
    public String uploadReel(Reel reel) {
        reel.setLikeCount(0);
        reelRepository.save(reel);
        return "Reel uploaded successfully";
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
        // simple version (no DB table yet)
        return "Comment added to reel " + id + ": " + comment;
    }

    /* ---------------- HOME FEED ---------------- */
    public List<Reel> getAllReels() {
        return reelRepository.findAllByOrderByIdDesc();
    }

    /* ---------------- EXPLORE FEED ---------------- */
    public List<Reel> getExploreReels() {
        return reelRepository.getExploreReels();
    }
}