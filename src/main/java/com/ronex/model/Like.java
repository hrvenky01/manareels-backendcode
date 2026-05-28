package com.ronex.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "likes",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"user_id", "reel_id"}
    )
)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reel_id", nullable = false)
    private Reel reel;

    // getters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Reel getReel() { return reel; }

    // setters
    public void setUser(User user) { this.user = user; }
    public void setReel(Reel reel) { this.reel = reel; }
}