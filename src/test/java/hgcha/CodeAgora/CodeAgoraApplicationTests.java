package hgcha.CodeAgora;

import hgcha.CodeAgora.entity.Post;
import hgcha.CodeAgora.entity.User;
import hgcha.CodeAgora.repository.PostRepository;
import hgcha.CodeAgora.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;

import java.util.Optional;

@SpringBootTest
class CodeAgoraApplicationTests {

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ApplicationContext applicationContext;

	@Test
	void contextLoads() {
	}

	@Test
	void testPostData() {
		User userA = userRepository.findByUsername("userA").get();
		for (int i = 0; i < 100; i++) {
			Post newPost = Post.builder()
							   .author(userA)
							   .title("테스트 제목입니다. " + i)
							   .content("테스트 내용입니다. " + i)
							   .build();
			postRepository.save(newPost);
		}
	}

	@Test
	void checkBean() {
		PageableHandlerMethodArgumentResolver bean = applicationContext.getBean(PageableHandlerMethodArgumentResolver.class);
		bean.
	}
}
