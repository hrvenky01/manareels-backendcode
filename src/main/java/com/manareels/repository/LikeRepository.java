package com.manareels.repository;

import com.manareels.model.Like;
import com.manareels.model.Reel;
import com.manareels.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndReel(User user, Reel reel);

    long countByReel(Reel reel);
}