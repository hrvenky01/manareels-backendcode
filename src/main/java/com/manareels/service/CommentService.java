package com.manareels.service;

import com.manareels.model.Comment;
import com.manareels.model.Reel;
import com.manareels.repository.CommentRepository;
import com.manareels.repository.ReelRepository;
import com.manareels.repository.UserRepository;
import com.manareels.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository repository;
    private final ReelRepository reelRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository repository,
                          ReelRepository reelRepository,
                          UserRepository userRepository) {
        this.repository = repository;
        this.reelRepository = reelRepository;
        this.userRepository = userRepository;
    }

    // ADD COMMENT
    public Comment addComment(Long reelId, Long userId, String text) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reel reel = reelRepository.findById(reelId)
                .orElseThrow(() -> new RuntimeException("Reel not found"));

        Comment c = new Comment();
        c.setUser(user);
        c.setReel(reel);
        c.setComment(text);

        return repository.save(c);
    }

    // GET COMMENTS
    public List<Comment> getComments(Long reelId) {

        Reel reel = reelRepository.findById(reelId)
                .orElseThrow(() -> new RuntimeException("Reel not found"));

        return repository.findByReelOrderByIdDesc(reel);
    }
}