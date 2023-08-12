package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.entity.Post;
import hgcha.CodeAgora.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/infoBoard")
public class InfoBoardController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, Model model) {
        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        return "post";
    }
}
