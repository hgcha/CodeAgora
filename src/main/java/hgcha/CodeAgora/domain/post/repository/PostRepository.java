package hgcha.CodeAgora.domain.post.repository;

import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostQueryDSLRepository {
    Page<Post> findAll(Pageable pageable);
    List<Post> findTop5ByAuthorOrderByCreatedAtDesc(User author);
}
