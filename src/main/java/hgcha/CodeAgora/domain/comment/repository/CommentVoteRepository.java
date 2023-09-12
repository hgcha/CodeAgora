package hgcha.CodeAgora.domain.comment.repository;

import hgcha.CodeAgora.domain.comment.entity.CommentVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {

    @Modifying
    @Transactional
    @Query("delete from CommentVote cv where cv.comment.id = :commentId and cv.user.id = :userId")
    void deleteByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("update CommentVote cv set cv.isLike = true where cv.comment.id = :commentId and cv.user.id = :userId")
    void upvoteByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("update CommentVote cv set cv.isLike = false where cv.comment.id = :commentId and cv.user.id = :userId")
    void downvoteByCommentIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);
}
