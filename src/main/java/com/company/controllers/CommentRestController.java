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
    public CommentResponse getCommentByCommentId(@PathVariable Long postId) {
        return commentService.getCommentByCommentId(postId);
    }

    @PostMapping("/{username}/{postId}/comment")
    public CommentResponse createComment(@PathVariable String username,
                                         @PathVariable Long postId,
                                         @RequestBody CommentRequest commentRequest) {
        return commentService.createComment(username, postId, commentRequest);
    }

    @PutMapping("/{username}/{postId}/{commentId}")
    public ResponseEntity<Object> updateCommentByCommentId(@PathVariable String username,
                                                           @PathVariable Long postId,
                                                           @PathVariable Long commentId,
                                                           @RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.updateCommentByCommentId(username, postId, commentId, commentRequest);
        if (commentResponse != null)
            return ResponseEntity.ok(commentResponse);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("comment is not update");
    }

    @DeleteMapping("/{username}/{postId}/{commentId}")
    public ResponseEntity<Object> deleteCommentByCommentId(@PathVariable String username,
                                                           @PathVariable Long postId,
                                                           @PathVariable Long commentId) {
        if (commentService.deleteCommentByCommentId(username, postId, commentId))
            return ResponseEntity.ok("this comment is deleted");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this comment is not yours");
    }
}
