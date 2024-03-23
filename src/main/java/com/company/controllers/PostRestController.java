package com.company.controllers;

import com.company.dto.request.PostRequest;
import com.company.dto.response.PostResponse;
import com.company.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {
    private final PostService postService;
    @GetMapping("/all")
    public List<PostResponse> getAllPost(){
     return postService.getAllPost();
    }
    @PostMapping
    public PostResponse createPostByUsername(@PathVariable String username,@RequestBody PostRequest postRequest){
        return postService.createPostByUsername(username,postRequest);
    }
}
