package hgcha.CodeAgora.service;

import hgcha.CodeAgora.dto.CommentCreateDto;
import hgcha.CodeAgora.dto.CommentUpdateDto;
import hgcha.CodeAgora.entity.Comment;
import hgcha.CodeAgora.repository.CommentRepository;
import hgcha.CodeAgora.repository.PostRepository;
import hgcha.CodeAgora.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        commentRepository.delete(comment);
    }

    public void update(CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(commentUpdateDto.getId()).orElseThrow();
        comment.setContent(commentUpdateDto.getContent());
        commentRepository.save(comment);
    }

    public void create(CommentCreateDto commentCreateDto) {
        Comment comment = Comment.builder()
                                 .post(postRepository.findById(commentCreateDto.getPostId()).orElseThrow())
                                 .author(userRepository.findByUsername(commentCreateDto.getUsername()).orElseThrow())
                                 .content(commentCreateDto.getContent())
                                 .build();

        commentRepository.save(comment);
    }
}
