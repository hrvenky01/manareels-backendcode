package com.manareels.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reels")
public class Reel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    private String videoUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    // ⭐ ADD THESE TWO (IMPORTANT)
    private Integer likeCount = 0;

    private Integer commentCount = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // ⭐ GETTERS/SETTERS FOR NEW FIELDS

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }
}