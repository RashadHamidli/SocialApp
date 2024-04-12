package com.company.services;

import com.company.config.CustomSecurityContext;
import com.company.dto.response.LikeResponse;
import com.company.entities.Comment;
import com.company.entities.Like;
import com.company.entities.Post;
import com.company.entities.User;
import com.company.repositories.CommentRepository;
import com.company.repositories.LikeRepository;
import com.company.repositories.PostRepository;
import com.company.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.StringTemplate.STR;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CustomSecurityContext securityContext;

    public List<LikeResponse> getAllLikeByUsername(String username) {
        List<Like> likes = likeRepository.findByUserUsername(username);
        return likes.stream().map(LikeResponse::converteLikeToLikeResponse).collect(Collectors.toList());
    }

    public List<LikeResponse> getAllLikeByPostId(Long postId) {
        List<Like> likes = likeRepository.findByPostPostId(postId);
        return likes.stream().map(LikeResponse::converteLikeToLikeResponse).collect(Collectors.toList());
    }

    public List<LikeResponse> getAllLikeByCommentId(Long commentId) {
        List<Like> likes = likeRepository.findByCommentCommentId(commentId);
        return likes.stream().map(LikeResponse::converteLikeToLikeResponse).toList();
    }

    @Transactional
    public LikeResponse creatLikeByPostId(Long postId) {
        String context = securityContext.getSecurityContext();
        User user = userRepository.findByUsername(context).orElseThrow(() -> new IllegalArgumentException(STR."\{context}" + " is not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId}" + " is not found"));
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        Like saveLike = likeRepository.save(like);
        return LikeResponse.converteLikeToLikeResponse(saveLike);
    }

    @Transactional
    public LikeResponse creatLikeByCommentId(Long commentId) {
        String context = securityContext.getSecurityContext();
        User user = userRepository.findByUsername(context).orElseThrow(() -> new IllegalArgumentException(STR."\{context}" + " is not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(STR."\{commentId} is not found"));
        Like like = new Like();
        like.setUser(user);
        like.setPost(comment.getPost());
        like.setComment(comment);
        Like savedLike = likeRepository.save(like);
        return LikeResponse.converteLikeToLikeResponse(savedLike);
    }

    @Transactional
    public boolean deleteLikeByPostId(Long postId, Long likeId) {
        String context = securityContext.getSecurityContext();
        Like foundLike = likeRepository.findById(likeId).orElseThrow(() -> new IllegalArgumentException(STR."\{likeId}" + " is not found"));
        if (foundLike != null && foundLike.getUser().getUsername().equals(context) && foundLike.getPost().getPostId().equals(postId)) {
            likeRepository.delete(foundLike);
            return true;
        } else
            return false;
    }


    public boolean deleteLikeByCommentId(Long commentId, Long likeId) {
        String context = securityContext.getSecurityContext();
        Like foundLike = likeRepository.findById(likeId).orElseThrow(() -> new IllegalArgumentException(STR."\{likeId}" + " is not found"));
        if (foundLike != null && foundLike.getUser().getUsername().equals(context) && foundLike.getPost().getPostId().equals(commentId)) {
            likeRepository.delete(foundLike);
            return true;
        } else
            return false;
    }
}
