package hgcha.CodeAgora.domain.report.entity;

import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User reporter;

    @ManyToOne
    private Post post;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Report(User reporter, Post post) {
        this.reporter = reporter;
        this.post = post;
    }
}
