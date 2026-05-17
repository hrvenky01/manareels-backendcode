package com.manareels.service;

import com.manareels.dto.FeedResponse;
import com.manareels.model.Reel;
import com.manareels.repository.ReelRepository;
import com.manareels.repository.LikeRepository;
import com.manareels.repository.CommentRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /* ---------------- PAGINATED FEED (FIXED) ---------------- */
    public Page<FeedResponse> getFeed(int page, int size) {

        Page<Reel> reels = reelRepo.findAll(
                PageRequest.of(page, size, Sort.by("createdAt").descending())
        );

        return reels.map(this::mapToResponse);
    }

    /* ---------------- MAPPER ---------------- */
    private FeedResponse mapToResponse(Reel reel) {

        return new FeedResponse(
                reel.getId(),
                reel.getCaption(),
                reel.getVideoUrl(),
                reel.getUser().getUsername(),
                likeRepo.countByReel(reel),
                commentRepo.countByReel(reel),
                reel.getCreatedAt()
        );
    }
}