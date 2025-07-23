package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogPostRequest {
    private String title;
    private String content;
    private String author;
}