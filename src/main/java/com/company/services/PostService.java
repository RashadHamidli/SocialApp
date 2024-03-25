package com.company.services;

import com.company.config.CustomSecurityContext;
import com.company.dto.request.PostRequest;
import com.company.dto.response.PostResponse;
import com.company.entities.Post;
import com.company.entities.User;
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
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CustomSecurityContext securityContext;

    public List<PostResponse> getAllPost() {
        List<Post> all = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
        return all.stream().map(PostResponse::convertePostToPostResponse).collect(Collectors.toList());
    }

    @Transactional
    public PostResponse createPostByUsername(PostRequest postRequest) {
        String context = securityContext.getSecurityContext();
        Post post = PostRequest.convertePostRequestToPost(postRequest);
        User user = userRepository.findByUsername(context).orElseThrow(() -> new IllegalArgumentException(STR."\{context}" + " is not find"));
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return PostResponse.convertePostToPostResponse(savedPost);
    }

    @Transactional
    public PostResponse updatePostByPostId(Long id, PostRequest postRequest) {
        String context = securityContext.getSecurityContext();
        Post foundPost = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(STR."\{id}" + " is not found"));
        Post requestToPost = PostRequest.convertePostRequestToPost(postRequest);
        if (foundPost != null && foundPost.getUser().getUsername().equals(context)) {
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
    public Boolean deletePostByPostId(Long id) {
        String context = securityContext.getSecurityContext();
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(STR."\{id}" + " is not found"));
        if (post != null && post.getUser().getUsername().equals(context)) {
            postRepository.delete(post);
            return true;
        } else
            return false;
    }
}
