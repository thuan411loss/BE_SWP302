package vn.BE_SWP302.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.BE_SWP302.domain.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}
