package hgcha.CodeAgora.domain.comment.entity;

import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User author;

    @ManyToOne
    private Post post;

    private String content;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    List<CommentVote> commentVotes;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Builder
    public Comment(User author, Post post, String content) {
        this.author = author;
        this.post = post;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(author, comment.author)
                && Objects.equals(post, comment.post)
                && Objects.equals(content, comment.content)
                && Objects.equals(createdAt, comment.createdAt)
                && Objects.equals(modifiedAt, comment.modifiedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, post, content, createdAt, modifiedAt);
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CommentVote> getLikes() {
        return commentVotes.stream().filter(commentVote -> commentVote.isLike()).toList();
    }

    public List<CommentVote> getDislikes() {
        return commentVotes.stream().filter(commentVote -> !commentVote.isLike()).toList();
    }

    public boolean isLikedByUser(User user) {
        return commentVotes.stream()
                    .filter(commentVote -> commentVote.isLike())
                    .anyMatch(commentVote -> commentVote.getUser().equals(user));
    }

    public boolean isDislikedByUser(User user) {
        return commentVotes.stream()
                           .filter(commentVote -> !commentVote.isLike())
                           .anyMatch(commentVote -> commentVote.getUser().equals(user));
    }

}
