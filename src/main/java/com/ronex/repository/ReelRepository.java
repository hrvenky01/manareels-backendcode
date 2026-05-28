package com.ronex.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ronex.model.Reel;

import java.time.LocalDateTime;
import java.util.List;

public interface ReelRepository extends JpaRepository<Reel, Long> {

    /* ---------------- CURSOR PAGINATION (B2 MAIN) ---------------- */
    @Query("""
        SELECT r FROM Reel r
        WHERE (:cursor IS NULL OR r.createdAt < :cursor)
        ORDER BY r.createdAt DESC
    """)
    List<Reel> findFeedCursor(@Param("cursor") LocalDateTime cursor,
                              Pageable pageable);

    /* ---------------- EXPLORE FEED ---------------- */
    @Query("""
        SELECT r FROM Reel r
        ORDER BY
        (COALESCE(r.likeCount, 0) * 3 +
         COALESCE(r.commentCount, 0) * 2) DESC,
        r.createdAt DESC
    """)
    List<Reel> getExploreReels();

    
    /* 🔥 SEARCH REELS BY CAPTION */
    List<Reel> findByCaptionContainingIgnoreCase(String query);

    /* ---------------- USER FEED ---------------- */
    @Query("SELECT r FROM Reel r WHERE r.user.id = :userId ORDER BY r.createdAt DESC")
    List<Reel> findReelsByUser(@Param("userId") Long userId);
}