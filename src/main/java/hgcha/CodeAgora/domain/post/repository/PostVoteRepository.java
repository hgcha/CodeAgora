package hgcha.CodeAgora.domain.post.repository;

import hgcha.CodeAgora.domain.post.entity.PostVote;
import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {
    Optional<PostVote> findByPostAndUser(Post post, User user);

    boolean existsByPostAndUser(Post post, User user);
}
