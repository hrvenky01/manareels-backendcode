package com.ronex.controller;

import org.springframework.web.bind.annotation.*;

import com.ronex.dto.LikeResponse;
import com.ronex.service.LikeService;

@RestController
@RequestMapping("/reels")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{id}/like")
    public LikeResponse toggleLike(@PathVariable Long id,
                                   @RequestParam String email) {
        return likeService.toggleLike(id, email);
    }
}