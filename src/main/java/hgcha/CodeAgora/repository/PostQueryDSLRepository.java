package hgcha.CodeAgora.repository;

import hgcha.CodeAgora.entity.Post;
import org.springframework.data.domain.Page;

public interface PostQueryDSLRepository {
    Page<Post> findByKeyword(String keyword, Integer page, Integer size);
}
