package hgcha.CodeAgora.domain.report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportDto {

    @NotNull
    private Long postId;

    @NotBlank
    private String reason;
}
