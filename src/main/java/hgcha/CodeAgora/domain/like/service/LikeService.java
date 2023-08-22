package hgcha.CodeAgora.domain.like.service;

import hgcha.CodeAgora.domain.like.dto.LikeDto;
import hgcha.CodeAgora.domain.like.entity.Like;
import hgcha.CodeAgora.domain.like.repository.LikeRepository;
import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.post.repository.PostRepository;
import hgcha.CodeAgora.domain.user.entity.User;
import hgcha.CodeAgora.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public void likeOrDislikePost(LikeDto likeDto) {
        Post post = postRepository.findById(likeDto.getPostId()).orElseThrow();
        User user = userRepository.findByUsername(likeDto.getUsername()).orElseThrow();

        likeRepository.findByPostAndUser(post, user).ifPresentOrElse(
            like -> likeRepository.delete(like),
            () -> likeRepository.save(new Like(post, user))
        );
    }

    public boolean existsByPostAndUser(Post post, User user) {
        return likeRepository.existsByPostAndUser(post, user);
    }
}
