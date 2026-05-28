package com.ronex.dto;

import java.util.List;

import com.ronex.model.Reel;

public class ProfileResponse {

    private String email;
    private List<Reel> reels;
    private int reelsCount;   // ✅ FIXED NAME
    private long totalLikes;

    public ProfileResponse(String email,
                           List<Reel> reels,
                           int reelsCount,
                           long totalLikes) {
        this.email = email;
        this.reels = reels;
        this.reelsCount = reelsCount;
        this.totalLikes = totalLikes;
    }

    public String getEmail() {
        return email;
    }

    public List<Reel> getReels() {
        return reels;
    }

    public int getReelsCount() {
        return reelsCount;
    }

    public long getTotalLikes() {
        return totalLikes;
    }
}