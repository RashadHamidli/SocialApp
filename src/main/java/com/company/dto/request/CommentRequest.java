package com.company.dto.request;

import com.company.entities.Comment;

import java.time.LocalDateTime;

public record CommentRequest(String text) {
    public static Comment converteCommentRequestToComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setText(commentRequest.text);
        comment.setCreateDate(LocalDateTime.now());
        return comment;
    }
}
