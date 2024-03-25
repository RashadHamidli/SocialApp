package com.company.controllers;

import com.company.dto.response.LikeResponse;
import com.company.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeRestController {
    private final LikeService likeService;

    @GetMapping("/{username}")
    public List<LikeResponse> getAllLikeByUsername(@PathVariable String username) {
        return likeService.getAllLikeByUsername(username);
    }

    @GetMapping("/{postId}")
    public List<LikeResponse> getAllLikeByPostId(@PathVariable Long postId) {
        return likeService.getAllLikeByPostId(postId);
    }

    @PostMapping("/{postId}")
    public LikeResponse creatLike(@PathVariable Long postId) {
        return likeService.creatLike(postId);
    }

    @GetMapping("/{postId}/{likeId}")
    public ResponseEntity<Object> deleteLikeByLikeId(@PathVariable Long postId,
                                                     @PathVariable Long likeId) {
        if (likeService.deleteLikeByLkeId(postId, likeId))
            return ResponseEntity.ok("unlike successfully");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unlike error!");
    }
}
