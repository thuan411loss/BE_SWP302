package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogPostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdDate;
}
