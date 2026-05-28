package com.ronex.dto;

import java.time.LocalDateTime;

public class FeedResponse {

    private Long postId;
    private String caption;
    private String videoUrl;
    private String username;
    private long likeCount;
    private long commentCount;
    private LocalDateTime createdAt;

    public FeedResponse(Long postId,
                        String caption,
                        String videoUrl,
                        String username,
                        long likeCount,
                        long commentCount,
                        LocalDateTime createdAt) {
        this.postId = postId;
        this.caption = caption;
        this.videoUrl = videoUrl;
        this.username = username;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
    }

    // getters

    public Long getPostId() { return postId; }
    public String getCaption() { return caption; }
    public String getVideoUrl() { return videoUrl; }
    public String getUsername() { return username; }
    public long getLikeCount() { return likeCount; }
    public long getCommentCount() { return commentCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}