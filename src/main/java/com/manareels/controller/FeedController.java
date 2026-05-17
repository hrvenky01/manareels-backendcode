package com.manareels.controller;

import com.manareels.dto.FeedResponse;
import com.manareels.service.FeedService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feed")
@CrossOrigin("*")
public class FeedController {

    private final FeedService service;

    public FeedController(FeedService service) {
        this.service = service;
    }

    @GetMapping
    public Page<FeedResponse> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getFeed(page, size);
    }
}