package com.company.services;

import com.company.config.CustomSecurityContext;
import com.company.dto.response.LikeResponse;
import com.company.entities.Like;
import com.company.entities.Post;
import com.company.entities.User;
import com.company.repositories.LikeRepository;
import com.company.repositories.PostRepository;
import com.company.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CustomSecurityContext securityContext;

    public List<LikeResponse> getAllLikeByUsername(String username) {
        List<Like> likes = likeRepository.findByUserUsername(username);
        return likes.stream().map(LikeResponse::converteLikeToLikeResponse).collect(Collectors.toList());
    }

    public List<LikeResponse> getAllLikeByPostId(Long postId) {
        List<Like> likes = likeRepository.findByPostPostId(postId);
        return likes.stream().map(LikeResponse::converteLikeToLikeResponse).collect(Collectors.toList());
    }

    @Transactional
    public LikeResponse creatLike(Long postId) {
        String context = securityContext.getSecurityContext();
        User user = userRepository.findByUsername(context).orElseThrow(() -> new IllegalArgumentException(STR."\{postId}" + " is not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId}" + " is not found"));
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        Like saveLike = likeRepository.save(like);
        return LikeResponse.converteLikeToLikeResponse(saveLike);
    }

    @Transactional
    public boolean deleteLikeByLkeId(Long postId, Long likeId) {
        String context = securityContext.getSecurityContext();
        Like foundLike = likeRepository.findById(likeId).orElseThrow(() -> new IllegalArgumentException(STR."\{likeId}" + " is not found"));
        if (foundLike != null && foundLike.getUser().getUsername().equals(context) && foundLike.getPost().getPostId().equals(postId)) {
            likeRepository.delete(foundLike);
            return true;
        } else
            return false;
    }
}
