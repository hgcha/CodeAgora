package hgcha.CodeAgora.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreateDto {

    @NotNull
    private Long postId;

    @NotNull
    private String username;

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;

}
