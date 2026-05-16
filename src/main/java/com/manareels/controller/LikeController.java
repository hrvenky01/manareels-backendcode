package com.manareels.controller;

import com.manareels.dto.LikeResponse;
import com.manareels.service.LikeService;
import org.springframework.web.bind.annotation.*;

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