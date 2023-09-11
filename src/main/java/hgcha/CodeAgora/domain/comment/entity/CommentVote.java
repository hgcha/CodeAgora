package hgcha.CodeAgora.domain.comment.entity;

import hgcha.CodeAgora.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment_vote")
@Getter
@NoArgsConstructor
public class CommentVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private boolean isLike;

    public CommentVote(Comment comment, User user, boolean like) {
        this.comment = comment;
        this.user = user;
        this.isLike = like;
    }

    public void setLike(boolean like) {
        this.isLike = like;
    }
}
