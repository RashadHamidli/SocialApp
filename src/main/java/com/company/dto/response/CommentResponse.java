package com.company.dto.response;

import com.company.entities.Comment;

import java.time.LocalDateTime;

public record CommentResponse(String postUsername,
                              String commentUsername,
                              Long postId,
                              Long commentId,
                              String text,
                              LocalDateTime createDate) {
    public static CommentResponse converteCommentToCommentResponse(Comment comment) {
        return new CommentResponse(comment.getPost().getUser().getUsername(),
                comment.getUser().getUsername(),
                comment.getPost().getPostId(),
                comment.getCommentId(),
                comment.getText(),
                comment.getCreateDate());
    }
}
