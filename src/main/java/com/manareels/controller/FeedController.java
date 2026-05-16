package com.manareels.controller;

import com.manareels.dto.FeedResponse;
import com.manareels.service.FeedService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
@CrossOrigin("*")
public class FeedController {

    private final FeedService service;

    public FeedController(FeedService service) {
        this.service = service;
    }

    @GetMapping
    public List<FeedResponse> getFeed() {
        return service.getFeed();
    }
}