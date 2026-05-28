package com.ronex.service;

import org.springframework.stereotype.Service;

import com.ronex.dto.ProfileResponse;
import com.ronex.model.Reel;
import com.ronex.model.User;
import com.ronex.repository.LikeRepository;
import com.ronex.repository.ReelRepository;
import com.ronex.repository.UserRepository;

import java.util.List;

@Service
public class ProfileService {

    private final ReelRepository reelRepo;
    private final LikeRepository likeRepo;
    private final UserRepository userRepo;

    public ProfileService(ReelRepository reelRepo,
                          LikeRepository likeRepo,
                          UserRepository userRepo) {
        this.reelRepo = reelRepo;
        this.likeRepo = likeRepo;
        this.userRepo = userRepo;
    }

    public ProfileResponse getProfile(String email) {

        // 1. Get user
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Get user reels (OPTIMIZED QUERY - NO FILTERING IN JAVA)
        List<Reel> reels = reelRepo.findReelsByUser(user.getId());

        // 3. Total likes across all reels
        long totalLikes = reels.stream()
                .mapToLong(r -> likeRepo.countByReel(r))
                .sum();

        // 4. Return response
        return new ProfileResponse(
                user.getEmail(),
                reels,
                reels.size(),
                totalLikes
        );
    }
}