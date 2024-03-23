package com.company.services;

import com.company.dto.request.PostRequest;
import com.company.dto.response.PostResponse;
import com.company.entities.Post;
import com.company.entities.User;
import com.company.repositories.PostRepository;
import com.company.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostResponse> getAllPost() {
        List<Post> all = postRepository.findAll();
        return all.stream().map(PostResponse::convertePostToPostResponse).collect(Collectors.toList());
    }

    @Transactional
    public PostResponse createPostByUsername(String username, PostRequest postRequest) {
        Post post = PostRequest.convertePostRequestToPost(postRequest);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username}" + " is not find"));
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return PostResponse.convertePostToPostResponse(savedPost);
    }

    @Transactional
    public PostResponse updatePostByPostId(String username, Long id, PostRequest postRequest) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username}" + " is not found"));
        Post foundPost = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(STR."\{id}" + " is not found"));
        Post requestToPost = PostRequest.convertePostRequestToPost(postRequest);
        if (foundPost.getUser().equals(user)) {
            Post post = updatePost(foundPost, requestToPost);
            return PostResponse.convertePostToPostResponse(post);
        } else
            return null;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public Post updatePost(Post foundPost, Post requestToPost) {
        Optional.ofNullable(requestToPost.getText()).ifPresent(foundPost::setText);
        return foundPost;
    }

    @Transactional
    public Boolean deletePostByPostId(String username, Long id) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{id}" + " is not found"));
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(STR."\{id}" + "is not found"));
        if (user.equals(post.getUser())) {
            postRepository.delete(post);
            return true;
        } else
            return false;
    }
}
