package vn.BE_SWP302.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.BE_SWP302.domain.request.BlogPostRequest;
import vn.BE_SWP302.domain.response.BlogPostResponse;
import vn.BE_SWP302.service.BlogPostService;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PostMapping
    public ResponseEntity<BlogPostResponse> create(@RequestBody BlogPostRequest request) {
        return ResponseEntity.ok(blogPostService.createPost(request));
    }

    @GetMapping
    public ResponseEntity<List<BlogPostResponse>> getAll() {
        return ResponseEntity.ok(blogPostService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(blogPostService.getPostById(id));
    }
}
