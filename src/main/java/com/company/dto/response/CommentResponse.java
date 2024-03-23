package com.company.dto.response;

import com.company.entities.Comment;

import java.time.LocalDateTime;

public record CommentResponse(String username,
                              Long id,
                              String text,
                              LocalDateTime createDate) {
    public static CommentResponse converteCommentToCommentResponse(Comment comment) {
        return new CommentResponse(comment.getUser().getUsername(), comment.getPost().getPost_id(), comment.getText(), comment.getCreateDate());
    }
}
