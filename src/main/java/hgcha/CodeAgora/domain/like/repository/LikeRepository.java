package hgcha.CodeAgora.domain.like.repository;

import hgcha.CodeAgora.domain.like.entity.Like;
import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostAndUser(Post post, User user);

    boolean existsByPostAndUser(Post post, User user);
}
