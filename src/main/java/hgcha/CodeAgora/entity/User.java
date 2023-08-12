package hgcha.CodeAgora.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    @CreationTimestamp
    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    @Builder
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
                && Objects.equals(email, user.email)
                && Objects.equals(joinedAt, user.joinedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, joinedAt);
    }
}
