package hgcha.CodeAgora.domain.post.entity;

import hgcha.CodeAgora.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post_vote")
@NoArgsConstructor
public class PostVote {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public PostVote(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
