package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.domain.report.dto.ReportProcessDto;
import hgcha.CodeAgora.domain.report.service.ReportService;
import hgcha.CodeAgora.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ReportService reportService;
    private final UserService userService;

    @GetMapping
    public String admin() {
        return "admin";
    }

    @GetMapping("/reportList")
    public String reportList(@RequestParam(defaultValue = "0") Integer page, Model model) {
        model.addAttribute("reports", reportService.findAll(page));
        return "reportList";
    }

    @GetMapping("/report/{reportId}")
    public String reportProcessForm(@PathVariable Long reportId, Model model) {
        model.addAttribute("report", reportService.findById(reportId));
        return "reportProcessForm";
    }

    @PostMapping("/report/{reportId}")
    public String processReport(ReportProcessDto reportProcessDto) {
        int period = reportProcessDto.getPeriod();

        if (period == 0) {
            return "redirect:/admin/reportList";
        }

        userService.banUser(reportProcessDto.getBannedId(), reportProcessDto.getPeriod());
        return "redirect:/admin/reportList";
    }
}
