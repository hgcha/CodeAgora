package hgcha.CodeAgora.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUpdateDto {

    @NotNull
    private Long id;

    @NotBlank(message = "빈 제목을 입력할 수 없습니다.")
    private String title;

    @NotBlank(message = "빈 내용을 입력할 수 없습니다.")
    private String content;

}


