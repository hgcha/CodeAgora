package hgcha.CodeAgora.domain.post.repository;

import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostQueryDSLRepository {
    Page<Post> findAll(Pageable pageable);

    List<Post> findTop5ByAuthorOrderByCreatedAtDesc(User author);

    @Query("""
            select p
            from Post p
            left join fetch p.author a
            left join fetch p.likes l
            left join fetch p.comments c
            left join fetch c.author ca
            left join fetch c.commentVotes cv
            where p.id = :postId
            """)
    Optional<Post> findPostDetails(@Param("postId") Long postId);

}
