package com.ronex.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @PostMapping("/{postId}")
    public String addComment(@PathVariable Long postId, @RequestBody String comment) {
        return "Comment added to post " + postId;
    }
}