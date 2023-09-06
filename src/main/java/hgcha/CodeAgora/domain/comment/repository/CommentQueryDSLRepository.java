package hgcha.CodeAgora.domain.comment.repository;

import hgcha.CodeAgora.domain.comment.entity.Comment;
import hgcha.CodeAgora.domain.post.dto.SearchConditionDto;
import org.springframework.data.domain.Page;

public interface CommentQueryDSLRepository {
    Page<Comment> findAllBySubjectAndKeyword(SearchConditionDto searchConditionDto, int page, int size);
}
