package com.ronex.service;

import org.springframework.stereotype.Service;

import com.ronex.dto.LikeResponse;
import com.ronex.model.Like;
import com.ronex.model.Reel;
import com.ronex.model.User;
import com.ronex.repository.LikeRepository;
import com.ronex.repository.ReelRepository;
import com.ronex.repository.UserRepository;

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