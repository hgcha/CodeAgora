package hgcha.CodeAgora.service;

import hgcha.CodeAgora.dto.SearchConditionDto;
import hgcha.CodeAgora.entity.Post;
import hgcha.CodeAgora.repository.CommentRepository;
import hgcha.CodeAgora.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Page<Post> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public void delete(Long id) {
        Post post = findById(id);
        post.getComments().forEach(commentRepository::delete);
        postRepository.delete(post);
    }

    public Page<Post> findAllByKeyword(SearchConditionDto searchConditionDto, Integer page, Integer size) {
        return postRepository.findBySubjectAndKeyword(searchConditionDto, page, size);
    }
}
