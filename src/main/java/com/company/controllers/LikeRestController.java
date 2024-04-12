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

    @GetMapping("/{username}/likes")
    public List<LikeResponse> getAllLikeByUsername(@PathVariable String username) {
        return likeService.getAllLikeByUsername(username);
    }

    @GetMapping("/{postId}/likes")
    public List<LikeResponse> getAllLikeByPostId(@PathVariable Long postId) {
        return likeService.getAllLikeByPostId(postId);
    }

    @GetMapping("/{commentId}/likes")
    public List<LikeResponse> getAllLikeByCommentId(@PathVariable Long commentId) {
        return likeService.getAllLikeByCommentId(commentId);
    }

    @PostMapping("/{postId}/likes")
    public LikeResponse creatLikeByPostId(@PathVariable Long postId) {
        return likeService.creatLikeByPostId(postId);
    }

    @PostMapping("/{commentId}/likes")
    public LikeResponse creatLikeByCommentId(@PathVariable Long commentId) {
        return likeService.creatLikeByCommentId(commentId);
    }

    @DeleteMapping("/{postId}/{likeId}")
    public ResponseEntity<Object> deleteLikeByPostId(@PathVariable Long postId,
                                                     @PathVariable Long likeId) {
        if (likeService.deleteLikeByPostId(postId, likeId))
            return ResponseEntity.ok("unlike successfully");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unlike error!");
    }
    @DeleteMapping("/{commentId}/{likeId}")
    public ResponseEntity<Object> deleteLikeByCommentId(@PathVariable Long postId,
                                                     @PathVariable Long likeId) {
        if (likeService.deleteLikeByCommentId(postId, likeId))
            return ResponseEntity.ok("unlike successfully");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unlike error!");
    }
}
