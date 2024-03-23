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

    @PostMapping("/{username}")
    public PostResponse createPostByUsername(@PathVariable String username,
                                             @RequestBody PostRequest postRequest) {
        return postService.createPostByUsername(username, postRequest);
    }

    @PutMapping("/{username}/{id}")
    public ResponseEntity<Object> updatePostByPostId2(@PathVariable String username,
                                                      @PathVariable Long id,
                                                      @RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.updatePostByPostId(username, id, postRequest);
        if (postResponse != null)
            return ResponseEntity.ok(postResponse);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("post is not updated");
    }

    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<Object> deletePostByPostId(@PathVariable String username,
                                                     @PathVariable Long id) {
        Boolean b = postService.deletePostByPostId(username, id);
        if (b.equals(true))
            return ResponseEntity.ok("this post is deleted");
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this post is not yours");
    }
}
