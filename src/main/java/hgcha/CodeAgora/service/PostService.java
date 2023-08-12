package hgcha.CodeAgora.service;

import hgcha.CodeAgora.entity.Post;
import hgcha.CodeAgora.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
