package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final PostService postService;

    @GetMapping
    public String reportForm(@RequestParam Long postId, Model model) {
        model.addAttribute("post", postService.findById(postId));
        return "reportForm";
    }
}
