package com.manareels.repository;

import com.manareels.model.Comment;
import com.manareels.model.Reel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByReelOrderByIdDesc(Reel reel);

    long countByReel(Reel reel);
}
