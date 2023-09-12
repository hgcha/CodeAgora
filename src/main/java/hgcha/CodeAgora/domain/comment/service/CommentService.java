package hgcha.CodeAgora.domain.comment.service;

import hgcha.CodeAgora.domain.comment.dto.CommentCreateDto;
import hgcha.CodeAgora.domain.comment.dto.CommentUpdateDto;
import hgcha.CodeAgora.domain.comment.dto.CommentVoteDto;
import hgcha.CodeAgora.domain.comment.entity.Comment;
import hgcha.CodeAgora.domain.comment.entity.CommentVote;
import hgcha.CodeAgora.domain.comment.repository.CommentRepository;
import hgcha.CodeAgora.domain.comment.repository.CommentVoteRepository;
import hgcha.CodeAgora.domain.post.dto.SearchConditionDto;
import hgcha.CodeAgora.domain.post.repository.PostRepository;
import hgcha.CodeAgora.domain.user.entity.User;
import hgcha.CodeAgora.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentVoteRepository commentVoteRepository;
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

    public List<Comment> findFiveRecentComments(User user) {
        return commentRepository.findTop5ByAuthorOrderByCreatedAtDesc(user);
    }

    public Page<Comment> findAllBySubjectAndKeyword(SearchConditionDto searchConditionDto, int page, int size) {
        return commentRepository.findAllBySubjectAndKeyword(searchConditionDto, page, size);
    }

    public void upvoteComment(CommentVoteDto commentVoteDto, User user) {
        if (commentVoteDto.getIsUpvoted()) {
            commentVoteRepository.deleteByCommentIdAndUserId(commentVoteDto.getCommentId(), user.getId());
        } else if (commentVoteDto.getIsDownvoted()) {
            commentVoteRepository.upvoteByCommentIdAndUserId(commentVoteDto.getCommentId(), user.getId());
        } else {
            commentVoteRepository.save(new CommentVote(commentRepository.getReferenceById(commentVoteDto.getCommentId()), user, true));
        }
    }

    public void downvoteComment(CommentVoteDto commentVoteDto, User user) {
        if (commentVoteDto.getIsUpvoted()) {
            commentVoteRepository.downvoteByCommentIdAndUserId(commentVoteDto.getCommentId(), user.getId());
        } else if (commentVoteDto.getIsDownvoted()) {
            commentVoteRepository.deleteByCommentIdAndUserId(commentVoteDto.getCommentId(), user.getId());
        } else {
            commentVoteRepository.save(new CommentVote(commentRepository.getReferenceById(commentVoteDto.getCommentId()), user, false));
        }
    }
}
