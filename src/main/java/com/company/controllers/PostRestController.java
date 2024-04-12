package com.company.controllers;

import com.company.dto.request.PostRequest;
import com.company.dto.response.PostResponse;
import com.company.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {
    private final PostService postService;

    @GetMapping("/all")
    public List<PostResponse> getAllPost() {
        return postService.getAllPost();
    }

    @PostMapping("/create")
    public PostResponse createPostByUser(@RequestBody PostRequest postRequest) {
        return postService.createPostByUsername(postRequest);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updatePostByPostId(@PathVariable Long id,
                                                     @RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.updatePostByPostId(id, postRequest);
        if (postResponse != null)
            return ResponseEntity.ok(postResponse);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("post is not updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostByPostId(@PathVariable Long id) {
        if (postService.deletePostByPostId(id))
            return ResponseEntity.ok("this post is deleted");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this post is not yours");
    }
}
