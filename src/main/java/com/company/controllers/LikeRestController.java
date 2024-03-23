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

    @GetMapping("/{postId}/likeId")
    public List<LikeResponse> getAllLikeByPostId(@PathVariable Long postId) {
        return likeService.getAllLikeByPostId(postId);
    }

    @GetMapping("/{username}/{postId}/like")
    public LikeResponse creatLike(@PathVariable String username, @PathVariable Long postId) {
        return likeService.creatLike(username, postId);
    }

    @GetMapping("/{username}/{postId}/{likeId}")
    public ResponseEntity<Object> deleteLikeByLikeId(@PathVariable String username, @PathVariable Long postId, @PathVariable Long likeId) {
        if (likeService.deleteLikeByLkeId(username, postId, likeId))
            return ResponseEntity.ok("unlike successfully");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unlike error!");
    }
}
