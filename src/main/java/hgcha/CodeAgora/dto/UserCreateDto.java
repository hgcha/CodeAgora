package hgcha.CodeAgora.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 16, message = "8 ~ 16자의 숫자, 문자, 특수기호를 입력해주세요.")
    private String password;


    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "[a-zA-Z0-9_.]+@[a-zA-Z0-9_.]+\\.[a-zA-Z0-9_.]+", message = "올바른 이메일 형식이 아닙니다.")
    private String email;

}
