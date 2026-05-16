package com.manareels.service;

import com.manareels.model.UserFollow;
import com.manareels.repository.UserFollowRepository;
import org.springframework.stereotype.Service;

@Service
public class UserFollowService {

    private final UserFollowRepository followRepository;

    public UserFollowService(UserFollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    /* ---------------- FOLLOW USER ---------------- */
    public String followUser(Long followerId, Long followingId) {

        // 🚨 prevent self-follow
        if (followerId == null || followingId == null) {
            return "Invalid user data";
        }

        if (followerId.equals(followingId)) {
            return "You cannot follow yourself";
        }

        // ✔ already following check
        boolean exists = followRepository
                .existsByFollowerIdAndFollowingId(followerId, followingId);

        if (exists) {
            return "Already following";
        }

        // ✔ create follow relation
        UserFollow follow = new UserFollow(followerId, followingId);
        followRepository.save(follow);

        return "Followed successfully";
    }

    /* ---------------- UNFOLLOW USER ---------------- */
    public String unfollowUser(Long followerId, Long followingId) {

        if (followerId == null || followingId == null) {
            return "Invalid user data";
        }

        boolean exists = followRepository
                .existsByFollowerIdAndFollowingId(followerId, followingId);

        if (!exists) {
            return "Not following user";
        }

        followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);

        return "Unfollowed successfully";
    }

    /* ---------------- CHECK FOLLOW STATUS ---------------- */
    public boolean isFollowing(Long followerId, Long followingId) {

        if (followerId == null || followingId == null) {
            return false;
        }

        return followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
    }
}