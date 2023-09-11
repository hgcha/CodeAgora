package hgcha.CodeAgora.domain.report.entity;

import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String reason;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Report(User reporter, Post post, String reason) {
        this.reporter = reporter;
        this.post = post;
        this.reason = reason;
    }
}
