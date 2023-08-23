package hgcha.CodeAgora.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentVoteDto {

    private Long commentId;

    private String username;

    private boolean isLike;
}
