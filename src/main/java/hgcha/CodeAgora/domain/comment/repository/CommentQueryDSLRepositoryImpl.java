package hgcha.CodeAgora.domain.comment.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hgcha.CodeAgora.domain.comment.entity.Comment;
import hgcha.CodeAgora.domain.post.dto.SearchConditionDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static hgcha.CodeAgora.domain.comment.entity.QComment.comment;

public class CommentQueryDSLRepositoryImpl implements CommentQueryDSLRepository {

    private final JPAQueryFactory query;

    public CommentQueryDSLRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Comment> findAllBySubjectAndKeyword(SearchConditionDto searchConditionDto, int page, int size) {

        BooleanExpression whereCondition = null;
        if (searchConditionDto.getSubject().equals("comment_content")) {
            whereCondition = comment.content.contains(searchConditionDto.getKeyword());
        } else if (searchConditionDto.getSubject().equals("comment_author")) {
            whereCondition = comment.author.username.eq(searchConditionDto.getKeyword());
        }

        List<Comment> comments = query.selectFrom(comment)
                                .where(whereCondition)
                                .orderBy(comment.createdAt.desc())
                                .fetch();

        return new PageImpl(comments.subList(page * size, Math.min(page * size + size, comments.size())), PageRequest.of(page, size), comments.size());

    }
}
