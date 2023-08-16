package hgcha.CodeAgora.service;

import hgcha.CodeAgora.dto.CommentCreateDto;
import hgcha.CodeAgora.dto.CommentUpdateDto;
import hgcha.CodeAgora.entity.Comment;
import hgcha.CodeAgora.repository.CommentRepository;
import hgcha.CodeAgora.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(NoSuchElementException::new);
        commentRepository.delete(comment);
    }

    public void update(CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(commentUpdateDto.getId()).orElseThrow(NoSuchElementException::new);
        comment.setContent(commentUpdateDto.getContent());
        commentRepository.save(comment);
    }

    public void create(CommentCreateDto commentCreateDto) {
        Comment comment = Comment.builder()
                                 .post(postRepository.findById(commentCreateDto.getPostId()).orElseThrow(NoSuchElementException::new))
                                 .content(commentCreateDto.getContent())
                                 .build();

        commentRepository.save(comment);
    }
}
