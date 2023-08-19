package hgcha.CodeAgora.repository;

import hgcha.CodeAgora.dto.SearchConditionDto;
import hgcha.CodeAgora.entity.Post;
import org.springframework.data.domain.Page;

public interface PostQueryDSLRepository {
    Page<Post> findBySubjectAndKeyword(SearchConditionDto searchConditionDto, Integer page, Integer size);
}
