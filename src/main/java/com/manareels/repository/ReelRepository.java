package com.manareels.repository;

import com.manareels.model.Reel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReelRepository extends JpaRepository<Reel, Long> {

    /* ---------------- HOME FEED (LATEST FIRST) ---------------- */
    List<Reel> findAllByOrderByIdDesc();

    /* ---------------- PAGINATED FEED (PHASE B BOOST) ---------------- */
    @Query("SELECT r FROM Reel r ORDER BY r.createdAt DESC")
    List<Reel> findFeed();

    /* ---------------- EXPLORE FEED (TRENDING ALGO) ---------------- */
    @Query("""
        SELECT r FROM Reel r
        ORDER BY
        (COALESCE(r.likeCount, 0) * 3 +
         COALESCE(r.commentCount, 0) * 2) DESC,
        r.createdAt DESC
    """)
    List<Reel> getExploreReels();

    /* ---------------- OPTIONAL: USER FEED (FUTURE FEATURE) ---------------- */
    @Query("SELECT r FROM Reel r WHERE r.user.id = :userId ORDER BY r.createdAt DESC")
    List<Reel> findReelsByUser(@Param("userId") Long userId);
}