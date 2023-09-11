package hgcha.CodeAgora.domain.post.repository;

import hgcha.CodeAgora.domain.post.entity.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {

    @Modifying
    @Transactional
    @Query("delete from PostVote pv where pv.post.id = :postId and pv.user.id = :userId")
    void deleteByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

}
