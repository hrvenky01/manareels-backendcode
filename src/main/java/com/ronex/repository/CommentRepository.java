package com.ronex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ronex.model.Comment;
import com.ronex.model.Reel;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /* ---------------- COMMENTS FOR A REEL ---------------- */
    List<Comment> findByReelOrderByIdDesc(Reel reel);

    /* ---------------- COMMENT COUNT (SINGLE) ---------------- */
    long countByReel(Reel reel);

    /* ---------------- BULK COMMENT COUNT (B3 - N+1 FIX) ---------------- */
    @Query("""
        SELECT c.reel.id, COUNT(c)
        FROM Comment c
        WHERE c.reel.id IN :reelIds
        GROUP BY c.reel.id
    """)
    List<Object[]> countCommentsByReelIds(@Param("reelIds") List<Long> reelIds);
}