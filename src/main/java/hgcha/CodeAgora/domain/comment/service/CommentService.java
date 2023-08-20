package hgcha.CodeAgora.domain.comment.service;

import hgcha.CodeAgora.domain.comment.dto.CommentCreateDto;
import hgcha.CodeAgora.domain.comment.dto.CommentUpdateDto;
import hgcha.CodeAgora.domain.comment.entity.Comment;
import hgcha.CodeAgora.domain.comment.repository.CommentRepository;
import hgcha.CodeAgora.domain.post.repository.PostRepository;
import hgcha.CodeAgora.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void create(CommentCreateDto commentCreateDto) {
        commentRepository.save(Comment.builder()
                                      .post(postRepository.findById(commentCreateDto.getPostId()).orElseThrow())
                                      .author(userRepository.findByUsername(commentCreateDto.getUsername()).orElseThrow())
                                      .content(commentCreateDto.getContent())
                                      .build());
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    public void updateComment(CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(commentUpdateDto.getId()).orElseThrow();
        comment.setContent(commentUpdateDto.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        commentRepository.delete(comment);
    }

}