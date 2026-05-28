package com.ronex.controller;

import org.springframework.web.bind.annotation.*;

import com.ronex.dto.FeedResponse;
import com.ronex.service.FeedService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/feed")
@CrossOrigin("*")
public class FeedController {

    private final FeedService service;

    public FeedController(FeedService service) {
        this.service = service;
    }

    /* ---------------- CURSOR FEED (B2) ---------------- */
    @GetMapping
    public List<FeedResponse> getFeed(
            @RequestParam(required = false) LocalDateTime cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getFeed(cursor, size);
    }
}