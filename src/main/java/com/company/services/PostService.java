package com.company.services;

import com.company.dto.request.PostRequest;
import com.company.dto.response.PostResponse;
import com.company.entities.Post;
import com.company.entities.User;
import com.company.repositories.PostRepository;
import com.company.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public PostResponse createPostByUsername(String username, PostRequest postRequest) {
        Post post = PostRequest.convertePostRequestToPost(postRequest);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username}" + " is not find"));
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return PostResponse.convertePostToPostResponse(savedPost);
    }
}
