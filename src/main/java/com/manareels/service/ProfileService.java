package com.manareels.service;

import com.manareels.dto.ProfileResponse;
import com.manareels.model.Reel;
import com.manareels.model.User;
import com.manareels.repository.LikeRepository;
import com.manareels.repository.ReelRepository;
import com.manareels.repository.UserRepository;
import org.springframework.stereotype.Service;

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

        // 2. Get user reels
        List<Reel> reels = reelRepo.findAllByOrderByIdDesc()
                .stream()
                .filter(r -> r.getUser().getId().equals(user.getId()))
                .toList();
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