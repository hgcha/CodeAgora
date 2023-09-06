package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.domain.post.service.PostService;
import hgcha.CodeAgora.domain.report.dto.ReportDto;
import hgcha.CodeAgora.domain.report.service.ReportService;
import hgcha.CodeAgora.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final PostService postService;
    private final ReportService reportService;

    @GetMapping
    public String reportForm(@RequestParam Long postId,
                             @RequestParam(name = "submitted", defaultValue = "false") Boolean submitted,
                             Model model) {
        model.addAttribute("post", postService.findById(postId));
        model.addAttribute("submitted", submitted);
        model.addAttribute("reportDto", new ReportDto(null, null));
        return "reportForm";
    }

    @PostMapping
    public String report(@Valid ReportDto reportDto,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal(expression = "user") User user,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "reportForm";
        }

        reportService.create(reportDto, user);
        redirectAttributes.addAttribute("postId", reportDto.getPostId());
        redirectAttributes.addAttribute("submitted", true);
        return "redirect:/report";
    }
}
