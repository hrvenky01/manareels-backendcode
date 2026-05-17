package com.manareels.controller;

import com.manareels.service.UserFollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserFollowController {

    private final UserFollowService followService;

    public UserFollowController(UserFollowService followService) {
        this.followService = followService;
    }

    /* ---------------- FOLLOW USER ---------------- */
    @PostMapping("/{userId}/follow")
    public ResponseEntity<String> followUser(
            @PathVariable Long userId,
            @RequestParam Long currentUserId
    ) {
        return ResponseEntity.ok(
                followService.followUser(currentUserId, userId)
        );
    }

    /* ---------------- UNFOLLOW USER ---------------- */
    @DeleteMapping("/{userId}/unfollow")
    public ResponseEntity<String> unfollowUser(
            @PathVariable Long userId,
            @RequestParam Long currentUserId
    ) {
        return ResponseEntity.ok(
                followService.unfollowUser(currentUserId, userId)
        );
    }

    /* ---------------- FOLLOW STATUS ---------------- */
    @GetMapping("/{userId}/follow-status")
    public ResponseEntity<Map<String, Boolean>> isFollowing(
            @PathVariable Long userId,
            @RequestParam Long currentUserId
    ) {
        boolean status = followService.isFollowing(currentUserId, userId);
        return ResponseEntity.ok(Map.of("isFollowing", status));
    }
}