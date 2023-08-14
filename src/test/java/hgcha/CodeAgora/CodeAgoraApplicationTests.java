package hgcha.CodeAgora;

import hgcha.CodeAgora.entity.Post;
import hgcha.CodeAgora.entity.User;
import hgcha.CodeAgora.repository.PostRepository;
import hgcha.CodeAgora.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CodeAgoraApplicationTests {

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

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

}
