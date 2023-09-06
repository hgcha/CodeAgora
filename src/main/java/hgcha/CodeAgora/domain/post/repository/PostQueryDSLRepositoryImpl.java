package hgcha.CodeAgora.domain.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hgcha.CodeAgora.domain.post.dto.SearchConditionDto;
import hgcha.CodeAgora.domain.post.entity.Post;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static hgcha.CodeAgora.domain.post.entity.QPost.post;

public class PostQueryDSLRepositoryImpl implements PostQueryDSLRepository {

    private final JPAQueryFactory query;

    public PostQueryDSLRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> findAllBySubjectAndKeyword(SearchConditionDto searchConditionDto, Integer page, Integer size) {

        BooleanExpression whereCondition = null;
        if (searchConditionDto.getSubject().equals("post_title")) {
            whereCondition = post.title.contains(searchConditionDto.getKeyword());
        } else if (searchConditionDto.getSubject().equals("post_content")) {
            whereCondition = post.content.contains(searchConditionDto.getKeyword());
        } else if (searchConditionDto.getSubject().equals("post_author")) {
            whereCondition = post.author.username.eq(searchConditionDto.getKeyword());
        }

        List<Post> posts = query.selectFrom(post)
                                .where(whereCondition)
                                .orderBy(post.createdAt.desc())
                                .fetch();

       return new PageImpl(posts.subList(page * size, Math.min(page * size + size, posts.size())), PageRequest.of(page, size), posts.size());
    }
}
