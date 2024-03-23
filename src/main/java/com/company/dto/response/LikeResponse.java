package com.company.dto.response;

import com.company.entities.Like;

public record LikeResponse(Long likeId,
                           String likedUsername,
                           String postUsername) {
    public static LikeResponse converteLikeToLikeResponse(Like like) {
        return new LikeResponse(like.getLike_id(), like.getUser().getUsername(), like.getPost().getUser().getUsername());
    }
}
