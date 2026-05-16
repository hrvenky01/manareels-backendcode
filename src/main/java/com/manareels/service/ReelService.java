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
        return reelRepository.save(reel);
    }

    /* ---------------- HOME FEED (LATEST) ---------------- */
    public List<Reel> getAllReels() {
        return reelRepository.findAllByOrderByIdDesc();
    }

    /* ---------------- EXPLORE FEED (🔥 NEW) ---------------- */
    public List<Reel> getExploreReels() {
        return reelRepository.getExploreReels();
    }
}