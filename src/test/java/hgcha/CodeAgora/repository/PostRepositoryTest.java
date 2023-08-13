package hgcha.CodeAgora.repository;

import hgcha.CodeAgora.entity.Post;
import hgcha.CodeAgora.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager em;

    User user;

    @BeforeEach
    void beforeEach() {
        user = User.builder()
                .username("userA")
                .password("1234")
                .email("userA@gmail.com")
                .build();

        em.persistAndFlush(user);
        em.clear();
    }

    @Test
    @DisplayName("게시글을 저장할 수 있다.")
    void save() {
        //given
        Post post = Post.builder()
                        .author(user)
                        .title("테스트 제목입니다.")
                        .content("테스트 내용입니다.")
                        .build();

        //when
        Post savedPost = postRepository.save(post);
        em.flush();
        em.clear();

        //then
        Post foundPost = postRepository.findById(savedPost.getId()).orElseThrow(NoSuchElementException::new);
        assertThat(foundPost.getAuthor()).isEqualTo(user);
        assertThat(foundPost.getTitle()).isEqualTo("테스트 제목입니다.");
        assertThat(foundPost.getContent()).isEqualTo("테스트 내용입니다.");
    }

    @Test
    @DisplayName("게시글을 삭제할 수 있다.")
    void delete() {
        //given
        Post postToDelete = Post.builder()
                           .author(user)
                           .title("삭제할 게시글의 제목입니다.")
                           .content("삭제할 게시글의 내용입니다.")
                           .build();

        Post savedPost = postRepository.saveAndFlush(postToDelete);
        em.clear();

        //when
        postRepository.delete(postToDelete);

        //then
        assertThat(postRepository.findById(savedPost.getId())).isEmpty();
    }


}