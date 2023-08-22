package hgcha.CodeAgora.domain.like.entity;

import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@NoArgsConstructor
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    public Like(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
