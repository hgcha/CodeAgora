package hgcha.CodeAgora.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportProcessDto {

    private Long bannedId;

    private Integer period;
}
