package hgcha.CodeAgora.domain.comment.repository;

import hgcha.CodeAgora.domain.comment.entity.Comment;
import hgcha.CodeAgora.domain.comment.entity.CommentVote;
import hgcha.CodeAgora.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
    Optional<CommentVote> findByCommentAndUser(Comment comment, User user);
}
