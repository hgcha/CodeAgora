package hgcha.CodeAgora.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostVoteDto {

    private Long postId;

    private String username;
}
