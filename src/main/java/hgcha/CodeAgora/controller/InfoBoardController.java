package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.dto.PostUpdateDto;
import hgcha.CodeAgora.entity.Comment;
import hgcha.CodeAgora.entity.Post;
import hgcha.CodeAgora.repository.CommentRepository;
import hgcha.CodeAgora.repository.PostRepository;
import hgcha.CodeAgora.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/infoBoard")
public class InfoBoardController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            Model model) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postService.findPosts(pageable);
        model.addAttribute("posts", posts);
        model.addAttribute("groupNumber", posts.getNumber() / 5);
        return "posts";
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, Model model) {
        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/write")
    public String postForm(Model model) {
        model.addAttribute("post", new Post());
        return "postCreateForm";
    }

    @PostMapping("/write")
    public String create(Post post) {
        System.out.println("post.getTitle() = " + post.getTitle());
        System.out.println("post.getContent() = " + post.getContent());
        Post savedPost = postRepository.save(post);
        return "redirect:/infoBoard/" + savedPost.getId();
    }

    @PostMapping("/{postId}/comments")
    public String createComment(@PathVariable Long postId, Comment comment) {
        Post post = postService.findById(postId);
        comment.setPost(post);
        commentRepository.save(comment);
        return "redirect:/infoBoard/" + postId;
    }

    @GetMapping("/{postId}/revise")
    public String postUpdateForm(@PathVariable Long postId, Model model) {
        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        return "postUpdateForm";
    }

    @PostMapping("/{postId}/revise")
    public String update(@PathVariable Long postId, PostUpdateDto postUpdateDto) {
        Post post = postService.findById(postId);
        post.setTitle(postUpdateDto.getTitle());
        post.setContent(postUpdateDto.getContent());
        postRepository.save(post);
        return "redirect:/infoBoard/" + postId;
    }
}
