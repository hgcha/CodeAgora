package hgcha.CodeAgora.domain.report.service;

import hgcha.CodeAgora.domain.post.repository.PostRepository;
import hgcha.CodeAgora.domain.report.dto.ReportDto;
import hgcha.CodeAgora.domain.report.entity.Report;
import hgcha.CodeAgora.domain.report.repository.ReportRepository;
import hgcha.CodeAgora.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;

    public void create(ReportDto reportDto, User reporter) {
        reportRepository.save(Report.builder()
                                    .post(postRepository.findById(reportDto.getPostId()).orElseThrow())
                                    .reporter(reporter)
                                    .build());
    }
}
