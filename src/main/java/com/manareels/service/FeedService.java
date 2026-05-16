package com.manareels.service;

import com.manareels.dto.FeedResponse;
import com.manareels.model.Reel;
import com.manareels.repository.ReelRepository;
import com.manareels.repository.LikeRepository;
import com.manareels.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<FeedResponse> getFeed() {

        List<Reel> reels = reelRepo.findAllByOrderByIdDesc();

        return reels.stream().map(reel -> new FeedResponse(
                reel.getId(),
                reel.getCaption(),
                reel.getVideoUrl(),
                reel.getUser().getUsername(),
                likeRepo.countByReel(reel),
                commentRepo.countByReel(reel),
                reel.getCreatedAt()
        )).collect(Collectors.toList());
    }
}