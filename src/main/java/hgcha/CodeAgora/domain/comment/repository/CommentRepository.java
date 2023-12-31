package hgcha.CodeAgora.domain.comment.repository;

import hgcha.CodeAgora.domain.comment.entity.Comment;
import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryDSLRepository {
    List<Comment> findAllByAuthor(User author);

    List<Comment> findAllByPost(Post post);

    List<Comment> findTop5ByAuthorOrderByCreatedAtDesc(User author);

    List<Comment> findAllByAuthor(User author, Pageable pageable);

}
