package com.company.controllers;

import com.company.dto.request.CommentRequest;
import com.company.dto.response.CommentResponse;
import com.company.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentRestController {
    private final CommentService commentService;

    @GetMapping("/all")
    public List<CommentResponse> getAllComment() {
        return commentService.getAllComment();
    }

    @GetMapping("/{postId}/comments")
    public List<CommentResponse> getCommentByCommentId(@PathVariable Long postId) {
        return commentService.getCommentByCommentId(postId);
    }

    @PostMapping("/{postId}/comment")
    public CommentResponse createComment(@PathVariable Long postId,
                                         @RequestBody CommentRequest commentRequest) {
        return commentService.createComment(postId, commentRequest);
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<Object> updateCommentByCommentId(@PathVariable Long postId,
                                                           @PathVariable Long commentId,
                                                           @RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.updateCommentByCommentId(postId, commentId, commentRequest);
        if (commentResponse != null)
            return ResponseEntity.ok(commentResponse);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("comment is not update");
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<Object> deleteCommentByCommentId(@PathVariable Long postId,
                                                           @PathVariable Long commentId) {
        if (commentService.deleteCommentByCommentId(postId, commentId))
            return ResponseEntity.ok("this comment is deleted");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this comment is not yours");
    }
}
