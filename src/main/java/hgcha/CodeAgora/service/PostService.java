package hgcha.CodeAgora.service;

import hgcha.CodeAgora.entity.Post;
import hgcha.CodeAgora.repository.CommentRepository;
import hgcha.CodeAgora.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
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
}
