package com.manareels.service;

import com.manareels.dto.LikeResponse;   // ✅ VERY IMPORTANT
import com.manareels.model.Like;
import com.manareels.model.Reel;
import com.manareels.model.User;
import com.manareels.repository.LikeRepository;
import com.manareels.repository.ReelRepository;
import com.manareels.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final LikeRepository likeRepo;
    private final ReelRepository reelRepo;
    private final UserRepository userRepo;

    public LikeService(LikeRepository likeRepo,
                       ReelRepository reelRepo,
                       UserRepository userRepo) {
        this.likeRepo = likeRepo;
        this.reelRepo = reelRepo;
        this.userRepo = userRepo;
    }

    public LikeResponse toggleLike(Long reelId, String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reel reel = reelRepo.findById(reelId)
                .orElseThrow(() -> new RuntimeException("Reel not found"));

        return likeRepo.findByUserAndReel(user, reel)
                .map(existingLike -> {
                    likeRepo.delete(existingLike);
                    long count = likeRepo.countByReel(reel);
                    return new LikeResponse(false, count);
                })
                .orElseGet(() -> {
                    Like like = new Like();
                    like.setUser(user);
                    like.setReel(reel);
                    likeRepo.save(like);
                    long count = likeRepo.countByReel(reel);
                    return new LikeResponse(true, count);
                });
    }
}