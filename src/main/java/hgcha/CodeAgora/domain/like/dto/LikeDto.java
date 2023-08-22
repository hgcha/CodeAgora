package hgcha.CodeAgora.domain.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeDto {

    private Long postId;

    private String username;
}
