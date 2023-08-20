package hgcha.CodeAgora.domain.post.service;

import hgcha.CodeAgora.domain.post.dto.PostCreateDto;
import hgcha.CodeAgora.domain.post.dto.PostUpdateDto;
import hgcha.CodeAgora.domain.post.dto.SearchConditionDto;
import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.comment.repository.CommentRepository;
import hgcha.CodeAgora.domain.post.repository.PostRepository;
import hgcha.CodeAgora.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    public Page<Post> findPosts(int page, int size) {
        return postRepository.findAll(PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    public Post createPost(PostCreateDto postCreateDto) {
        return postRepository.save(Post.builder()
                                       .author(userRepository.findByUsername(postCreateDto.getUsername()).orElseThrow())
                                       .title(postCreateDto.getTitle())
                                       .content(postCreateDto.getContent())
                                       .build());
    }

    public void deletePost(Long id) {
        postRepository.delete(findById(id));
    }

    public void updatePost(PostUpdateDto postUpdateDto) {
        Post post = findById(postUpdateDto.getId());
        post.setTitle(postUpdateDto.getTitle());
        post.setContent(postUpdateDto.getContent());
        postRepository.save(post);
    }

    public Page<Post> findAllBySubjectAndKeyword(SearchConditionDto searchConditionDto, Integer page, Integer size) {
        return postRepository.findAllBySubjectAndKeyword(searchConditionDto, page, size);
    }
}
