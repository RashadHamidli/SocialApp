package com.company.services;

import com.company.dto.request.CommentRequest;
import com.company.dto.response.CommentResponse;
import com.company.entities.Comment;
import com.company.entities.Post;
import com.company.entities.User;
import com.company.repositories.CommentRepository;
import com.company.repositories.PostRepository;
import com.company.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public List<CommentResponse> getAllComment() {
        List<Comment> all = commentRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
        return all.stream().map(CommentResponse::converteCommentToCommentResponse).collect(Collectors.toList());
    }

    public CommentResponse getCommentByCommentId(Long postId) {
        Comment foundComment = commentRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId}" + " is not found"));
        return CommentResponse.converteCommentToCommentResponse(foundComment);
    }

    @Transactional
    public CommentResponse createComment(String username,
                                         Long postId,
                                         CommentRequest commentRequest) {
        Comment requestToComment = CommentRequest.converteCommentRequestToComment(commentRequest);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username}" + " is not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId}" + " is not found"));
        requestToComment.setUser(user);
        requestToComment.setPost(post);
        Comment savedComment = commentRepository.save(requestToComment);
        return CommentResponse.converteCommentToCommentResponse(savedComment);
    }

    @Transactional
    public CommentResponse updateCommentByCommentId(String username,
                                                    Long postId,
                                                    Long commentId,
                                                    CommentRequest commentRequest) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username}" + " is not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId}" + " is not found"));
        Comment foundComment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException(STR."\{postId}" + " is not found"));
        Comment requestToComment = CommentRequest.converteCommentRequestToComment(commentRequest);
        if (requestToComment.getUser().equals(user) && requestToComment.getPost().getPost_id().equals(post.getPost_id())) {
            Comment comment = updateComment(foundComment, requestToComment);
            Comment savedComment = commentRepository.save(comment);
            return CommentResponse.converteCommentToCommentResponse(savedComment);
        }
        return null;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public Comment updateComment(Comment foundComment, Comment requestToComment) {
        Optional.ofNullable(requestToComment.getText()).ifPresent(foundComment::setText);
        return foundComment;
    }

}
