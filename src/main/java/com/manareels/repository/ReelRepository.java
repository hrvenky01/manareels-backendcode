package com.manareels.repository;

import com.manareels.model.Reel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReelRepository extends JpaRepository<Reel, Long> {

    /* ---------------- HOME FEED ---------------- */
    List<Reel> findAllByOrderByIdDesc();

    /* ---------------- EXPLORE FEED (TRENDING ALGO) ---------------- */
    @Query("""
        SELECT r FROM Reel r
        ORDER BY
        (COALESCE(r.likeCount, 0) * 3 +
         COALESCE(r.commentCount, 0) * 2) DESC,
        r.id DESC
    """)
    List<Reel> getExploreReels();
}