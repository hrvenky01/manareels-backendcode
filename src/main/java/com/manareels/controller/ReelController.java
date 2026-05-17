package com.manareels.controller;

import com.manareels.dto.FeedResponse;
import com.manareels.model.Reel;
import com.manareels.service.FeedService;
import com.manareels.service.ReelService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reels")
@CrossOrigin(origins = "*")
public class ReelController {

    private final FeedService feedService;
    private final ReelService reelService;

    public ReelController(FeedService feedService,
                          ReelService reelService) {
        this.feedService = feedService;
        this.reelService = reelService;
    }

    /* ---------------- HOME FEED (PAGINATED - PHASE B FIX) ---------------- */
    @GetMapping("/feed")
    public Page<FeedResponse> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return feedService.getFeed(page, size);
    }

    /* ---------------- EXPLORE FEED ---------------- */
    @GetMapping("/explore")
    public List<Reel> getExploreReels() {
        return reelService.getExploreReels();
    }

    /* ---------------- UPLOAD REEL (PHASE A READY) ---------------- */
    @PostMapping("/upload")
    public String uploadReel(@RequestBody Reel reel) {
        return reelService.uploadReel(reel);
    }

    /* ---------------- LIKE REEL ---------------- */
    @PostMapping("/{id}/like")
    public String likeReel(@PathVariable Long id) {
        return reelService.likeReel(id);
    }

    /* ---------------- COMMENT REEL ---------------- */
    @PostMapping("/{id}/comment")
    public String commentReel(@PathVariable Long id,
                              @RequestParam String comment) {
        return reelService.commentReel(id, comment);
    }
}