package hgcha.CodeAgora.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hgcha.CodeAgora.dto.SearchConditionDto;
import hgcha.CodeAgora.entity.Post;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static hgcha.CodeAgora.entity.QPost.post;

@Slf4j
public class PostQueryDSLRepositoryImpl implements PostQueryDSLRepository {

    private final JPAQueryFactory query;

    public PostQueryDSLRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> findBySubjectAndKeyword(SearchConditionDto searchConditionDto, Integer page, Integer size) {

        BooleanExpression whereCondition = null;
        if (searchConditionDto.getSubject().equals("title")) {
            whereCondition = post.title.contains(searchConditionDto.getKeyword());
        } else if (searchConditionDto.getSubject().equals("content")) {
            whereCondition = post.content.contains(searchConditionDto.getKeyword());
        } else if (searchConditionDto.getSubject().equals("author")) {
            whereCondition = post.author.username.eq(searchConditionDto.getKeyword());
        }

        List<Post> posts = query.selectFrom(post)
                                .where(whereCondition)
                                .orderBy(post.createdAt.desc())
                                .fetch();

       return new PageImpl(posts.subList(page * size, Math.min(page * size + size, posts.size())), PageRequest.of(page, size), posts.size());
    }
}
