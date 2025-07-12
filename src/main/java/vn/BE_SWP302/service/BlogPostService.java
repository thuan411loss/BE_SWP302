package vn.BE_SWP302.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.BE_SWP302.domain.BlogPost;
import vn.BE_SWP302.domain.request.BlogPostRequest;
import vn.BE_SWP302.domain.response.BlogPostResponse;
import vn.BE_SWP302.repository.BlogPostRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostResponse createPost(BlogPostRequest request) {
        BlogPost post = new BlogPost();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(request.getAuthor());
        BlogPost saved = blogPostRepository.save(post);
        return mapToResponse(saved);
    }

    public List<BlogPostResponse> getAllPosts() {
        return blogPostRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BlogPostResponse getPostById(Long id) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToResponse(post);
    }

    private BlogPostResponse mapToResponse(BlogPost post) {
        BlogPostResponse res = new BlogPostResponse();
        res.setId(post.getId());
        res.setTitle(post.getTitle());
        res.setContent(post.getContent());
        res.setAuthor(post.getAuthor());
        res.setCreatedDate(post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return res;
    }
}