package hgcha.CodeAgora.repository;

import hgcha.CodeAgora.domain.user.repository.UserRepository;
import hgcha.CodeAgora.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestEntityManager em;

    @Test
    @DisplayName("유저를 저장할 수 있다.")
    void save() {
        //given
        User user = User.builder()
                        .username("userA")
                        .password("1234")
                        .email("userA@gmail.com")
                        .build();

        //when
        userRepository.save(user);
        em.flush();
        em.clear();

        //then
        assertThat(userRepository.findByUsername("userA")).contains(user);
    }

    @Test
    @DisplayName("유저를 삭제할 수 있다.")
    void delete() {
        //given
        User user = User.builder()
                        .username("userA")
                        .password("1234")
                        .email("userA@gmail.com")
                        .build();

        userRepository.save(user);
        em.flush();
        em.clear();

        //when
        userRepository.delete(user);

        //then
        assertThat(userRepository.findByUsername("userA")).isEmpty();
    }
}