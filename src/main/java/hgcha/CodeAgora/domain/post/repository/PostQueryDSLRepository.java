package hgcha.CodeAgora.domain.post.repository;

import hgcha.CodeAgora.domain.post.dto.SearchConditionDto;
import hgcha.CodeAgora.domain.post.entity.Post;
import org.springframework.data.domain.Page;

public interface PostQueryDSLRepository {
    Page<Post> findAllBySubjectAndKeyword(SearchConditionDto searchConditionDto, Integer page, Integer size);
}
