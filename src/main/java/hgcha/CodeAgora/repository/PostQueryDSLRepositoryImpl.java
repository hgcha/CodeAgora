package hgcha.CodeAgora.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hgcha.CodeAgora.entity.Post;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static hgcha.CodeAgora.entity.QPost.post;

@Slf4j
public class PostQueryDSLRepositoryImpl implements PostQueryDSLRepository {

    private final JPAQueryFactory query;

    public PostQueryDSLRepositoryImpl(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> findByKeyword(String keyword, Integer page, Integer size) {
        List<Post> posts = query.selectFrom(post)
                                .where(post.title.contains(keyword))
                                .orderBy(post.createdAt.desc())
                                .fetch();

        Page<Post> result = new PageImpl(posts.subList(page * size, Math.min(page * size + size, posts.size())), PageRequest.of(page, size), posts.size());
        log.info("result.getTotalPages()={}", result.getTotalPages());
        log.info("result.getTotalElement()={}", result.getTotalElements());
        log.info("result.getNumber()={}", result.getNumber());
        return result;
    }
}
