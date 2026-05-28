package com.ronex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ronex.model.Like;
import com.ronex.model.Reel;
import com.ronex.model.User;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    /* ---------------- CHECK LIKE ---------------- */
    Optional<Like> findByUserAndReel(User user, Reel reel);

    /* ---------------- SINGLE COUNT (OK, BUT SLOW FOR FEED) ---------------- */
    long countByReel(Reel reel);

    /* ---------------- BULK COUNT (B3 - N+1 FIX 🔥) ---------------- */
    @Query("""
        SELECT l.reel.id, COUNT(l)
        FROM Like l
        WHERE l.reel.id IN :reelIds
        GROUP BY l.reel.id
    """)
    List<Object[]> countLikesByReelIds(@Param("reelIds") List<Long> reelIds);
}