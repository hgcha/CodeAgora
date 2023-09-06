package hgcha.CodeAgora.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordDto {

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    @Size(min = 8, max = 16, message = "8 ~ 16자의 숫자, 문자, 특수기호를 입력해주세요.")
    private String newPassword;

    @NotBlank(message = "새 비밀번호를 다시 한번 입력해주세요.")
    @Size(min = 8, max = 16, message = "8 ~ 16자의 숫자, 문자, 특수기호를 입력해주세요.")
    private String newPasswordForCheck;

}
