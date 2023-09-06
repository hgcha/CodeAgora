package hgcha.CodeAgora.domain.report.service;

import hgcha.CodeAgora.domain.post.repository.PostRepository;
import hgcha.CodeAgora.domain.report.dto.ReportDto;
import hgcha.CodeAgora.domain.report.entity.Report;
import hgcha.CodeAgora.domain.report.repository.ReportRepository;
import hgcha.CodeAgora.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
                                    .reason(reportDto.getReason())
                                    .build());
    }

    public Page<Report> findAll(int page) {
        return reportRepository.findAll(PageRequest.of(page, 10));
    }

    public Report findById(Long id) {
        return reportRepository.findById(id).orElseThrow();
    }
}
