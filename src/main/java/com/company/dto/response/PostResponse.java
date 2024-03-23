package com.company.dto.response;

import com.company.entities.Post;

import java.time.LocalDateTime;

public record PostResponse(String username,
                           Long postId,
                           String text,
                           LocalDateTime createDate) {
    public static PostResponse convertePostToPostResponse(Post post){
        return new PostResponse(post.getUser().getUsername(),post.getPost_id(),post.getText(),post.getCreateDate());
    }
}
