package com.manareels.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "user_follows",
    uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"})
)
public class UserFollow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_id", nullable = false)
    private Long followerId;

    @Column(name = "following_id", nullable = false)
    private Long followingId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* ---------------- CONSTRUCTOR ---------------- */

    public UserFollow() {
        this.createdAt = LocalDateTime.now();
    }

    public UserFollow(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
        this.createdAt = LocalDateTime.now();
    }

    /* ---------------- GETTERS / SETTERS ---------------- */

    public Long getId() {
        return id;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}