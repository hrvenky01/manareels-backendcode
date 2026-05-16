package com.manareels.controller;

import com.manareels.dto.FeedResponse;
import com.manareels.model.Reel;
import com.manareels.service.FeedService;
import com.manareels.service.ReelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reels")
public class ReelController {

    private final FeedService feedService;
    private final ReelService reelService;

    public ReelController(FeedService feedService,
                          ReelService reelService) {
        this.feedService = feedService;
        this.reelService = reelService;
    }

    /* ---------------- HOME FEED ---------------- */
    @GetMapping("/feed")
    public List<FeedResponse> getFeed() {
        return feedService.getFeed();
    }

    /* ---------------- EXPLORE FEED (🔥 PHASE 2) ---------------- */
    @GetMapping("/explore")
    public List<Reel> getExploreReels() {
        return reelService.getExploreReels();
    }
}