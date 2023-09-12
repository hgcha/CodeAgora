package hgcha.CodeAgora.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentVoteDto {

    private Long commentId;

    private Boolean isUpvoted;

    private Boolean isDownvoted;
}
