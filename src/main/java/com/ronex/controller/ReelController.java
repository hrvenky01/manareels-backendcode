package com.ronex.controller;

import com.ronex.dto.FeedResponse;
import com.ronex.model.Reel;
import com.ronex.model.User;
import com.ronex.repository.UserRepository;
import com.ronex.service.FeedService;
import com.ronex.service.ReelService;
import com.ronex.utils.SecurityUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reels")
@CrossOrigin(origins = "*")
public class ReelController {

    private final FeedService feedService;
    private final ReelService reelService;
    private final UserRepository userRepository;

    public ReelController(
            FeedService feedService,
            ReelService reelService,
            UserRepository userRepository
    ) {
        this.feedService = feedService;
        this.reelService = reelService;
        this.userRepository = userRepository;
    }

    /* ---------------- HOME FEED (CURSOR PAGINATION) ---------------- */
    @GetMapping("/feed")
    public List<FeedResponse> getFeed(
            @RequestParam(required = false) LocalDateTime cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        return feedService.getFeed(cursor, size);
    }

    /* ---------------- EXPLORE FEED ---------------- */
    @GetMapping("/explore")
    public List<Reel> getExploreReels() {
        return reelService.getExploreReels();
    }
    @GetMapping("/search")
    public List<Reel> searchReels(@RequestParam String query) {
        return reelService.searchReels(query);
    }

    /* ---------------- UPLOAD REEL (JWT USER) ---------------- */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadReel(
            @RequestParam MultipartFile video,
            @RequestParam String caption
    ) {
        // 🔐 JWT nunchi logged-in user
        String email = SecurityUtils.getCurrentUsername();

        if (email == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reel reel = reelService.uploadReel(user, video, caption);
        return ResponseEntity.ok(reel);
    }

    /* ---------------- COMMENT REEL ---------------- */
    @PostMapping("/{id}/comment")
    public String commentReel(
            @PathVariable Long id,
            @RequestParam String comment
    ) {
        return reelService.commentReel(id, comment);
    }
}