package hgcha.CodeAgora.repository;

import hgcha.CodeAgora.entity.Comment;
import hgcha.CodeAgora.entity.Post;
import hgcha.CodeAgora.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAuthor(User author);

    List<Comment> findAllByPost(Post post);
}
