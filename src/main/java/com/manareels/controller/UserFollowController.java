package com.manareels.controller;

import com.manareels.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserFollowController {

    @Autowired
    private UserFollowService followService;

    // FOLLOW
    @PostMapping("/{userId}/follow")
    public String followUser(
            @PathVariable Long userId,
            @RequestParam Long currentUserId
    ) {
        return followService.followUser(currentUserId, userId);
    }

    // UNFOLLOW
    @DeleteMapping("/{userId}/unfollow")
    public String unfollowUser(
            @PathVariable Long userId,
            @RequestParam Long currentUserId
    ) {
        return followService.unfollowUser(currentUserId, userId);
    }

    // FOLLOW STATUS
    @GetMapping("/{userId}/follow-status")
    public Map<String, Boolean> isFollowing(
            @PathVariable Long userId,
            @RequestParam Long currentUserId
    ) {
        boolean status = followService.isFollowing(currentUserId, userId);
        return Map.of("isFollowing", status);
    }
}