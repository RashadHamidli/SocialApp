package com.company.dto.request;

import com.company.entities.Post;

import java.time.LocalDateTime;

public record PostRequest(String text) {
    public static Post convertePostRequestToPost(PostRequest postRequest){
        Post post = new Post();
        post.setText(postRequest.text);
        post.setCreateDate(LocalDateTime.now());
        return post;
    }
}
