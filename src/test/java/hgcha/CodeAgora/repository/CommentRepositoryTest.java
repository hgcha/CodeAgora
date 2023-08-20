package hgcha.CodeAgora.repository;

import hgcha.CodeAgora.domain.comment.entity.Comment;
import hgcha.CodeAgora.domain.comment.repository.CommentRepository;
import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    private User user;

    private Post post;

    @BeforeEach
    void beforeEach() {
        user = User.builder()
                   .username("userA")
                   .password("1234")
                   .email("userA@gmail.com")
                   .build();

        em.persist(user);

        post = Post.builder()
                   .author(user)
                   .title("테스트 제목입니다.")
                   .content("테스트 내용입니다.")
                   .build();

        em.persist(post);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("댓글을 저장할 수 있다.")
    void save() {
        //given
        Comment comment = Comment.builder()
                                 .post(post)
                                 .author(user)
                                 .content("테스트 댓글 내용입니다.")
                                 .build();

        //when
        Comment savedComment = commentRepository.save(comment);
        em.flush();
        em.clear();

        //then
        assertThat(commentRepository.findById(savedComment.getId())).contains(comment);
    }

    @Test
    @DisplayName("댓글을 삭제할 수 있다.")
    void delete() {
        //given
        Comment comment = Comment.builder()
                                 .post(post)
                                 .author(user)
                                 .content("테스트 댓글 내용입니다.")
                                 .build();

        Comment savedComment = commentRepository.save(comment);
        em.flush();
        em.clear();

        //when
        commentRepository.delete(comment);

        //then
        assertThat(commentRepository.findById(savedComment.getId())).isEmpty();
    }

    @Test
    @DisplayName("유저가 작성한 댓글을 조회할 수 있다.")
    void findCommentsByUser() {
        //given
        User otherUser = User.builder()
                             .username("userB")
                             .password("5678")
                             .email("userB@naver.com")
                             .build();
        em.persist(otherUser);

        Post otherPost = Post.builder()
                             .author(otherUser)
                             .title("테스트 제목2 입니다.")
                             .content("테스트 내용2 입니다.")
                             .build();
        em.persist(otherPost);

        Comment comment1 = Comment.builder()
                                  .post(post)
                                  .author(user)
                                  .content("테스트 댓글 내용1 입니다.")
                                  .build();

        Comment comment2 = Comment.builder()
                                  .post(otherPost)
                                  .author(user)
                                  .content("테스트 댓글 내용2 입니다.")
                                  .build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        em.flush();
        em.clear();

        //when
        List<Comment> comments = commentRepository.findAllByAuthor(user);

        //then
        assertThat(comments.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("한 게시글에 작성된 댓글을 조회할 수 있다.")
    void findCommentsByPost() {
        //given
        User otherUser = User.builder()
                             .username("userB")
                             .password("5678")
                             .email("userB@naver.com")
                             .build();
        em.persist(otherUser);

        Comment comment1 = Comment.builder()
                                  .post(post)
                                  .author(user)
                                  .content("테스트 댓글 내용1 입니다.")
                                  .build();

        Comment comment2 = Comment.builder()
                                  .post(post)
                                  .author(otherUser)
                                  .content("테스트 댓글 내용2 입니다.")
                                  .build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        em.flush();
        em.clear();

        //when
        List<Comment> comments = commentRepository.findAllByPost(post);

        //then
        assertThat(comments.size()).isEqualTo(2);
    }
}