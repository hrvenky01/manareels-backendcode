package com.ronex.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ronex.dto.FeedResponse;
import com.ronex.model.Reel;
import com.ronex.repository.CommentRepository;
import com.ronex.repository.LikeRepository;
import com.ronex.repository.ReelRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final ReelRepository reelRepo;
    private final LikeRepository likeRepo;
    private final CommentRepository commentRepo;

    public FeedService(ReelRepository reelRepo,
                       LikeRepository likeRepo,
                       CommentRepository commentRepo) {
        this.reelRepo = reelRepo;
        this.likeRepo = likeRepo;
        this.commentRepo = commentRepo;
    }

    /* ---------------- CURSOR FEED (B2 + B3 FINAL 🔥) ---------------- */
    public List<FeedResponse> getFeed(LocalDateTime cursor, int size) {

        // 1️⃣ Fetch reels using cursor pagination
        List<Reel> reels = reelRepo.findFeedCursor(
                cursor,
                PageRequest.of(0, size)
        );

        if (reels.isEmpty()) {
            return List.of();
        }

        // 2️⃣ Collect reel IDs
        List<Long> reelIds = reels.stream()
                .map(Reel::getId)
                .toList();

        // 3️⃣ Bulk like counts
        Map<Long, Long> likeCountMap = likeRepo.countLikesByReelIds(reelIds)
                .stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));

        // 4️⃣ Bulk comment counts
        Map<Long, Long> commentCountMap = commentRepo.countCommentsByReelIds(reelIds)
                .stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));

        // 5️⃣ Map to response
        return reels.stream()
                .map(reel -> new FeedResponse(
                        reel.getId(),
                        reel.getCaption(),
                        reel.getVideoUrl(),
                        reel.getUser().getUsername(),
                        likeCountMap.getOrDefault(reel.getId(), 0L),
                        commentCountMap.getOrDefault(reel.getId(), 0L),
                        reel.getCreatedAt()
                ))
                .toList();
    }
}