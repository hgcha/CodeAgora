package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.dto.CommentCreateDto;
import hgcha.CodeAgora.dto.CommentUpdateDto;
import hgcha.CodeAgora.dto.PostCreateDto;
import hgcha.CodeAgora.dto.PostUpdateDto;
import hgcha.CodeAgora.entity.Comment;
import hgcha.CodeAgora.entity.Post;
import hgcha.CodeAgora.repository.CommentRepository;
import hgcha.CodeAgora.repository.PostRepository;
import hgcha.CodeAgora.service.CommentService;
import hgcha.CodeAgora.service.PostService;
import hgcha.CodeAgora.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping({"/infoBoard", "/"})
@Slf4j
public class InfoBoardController {

    private final PostService postService;
    private final UserService userService;
    private final PostRepository postRepository;
    private final CommentService commentService;

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
        log.info("post={}", post.getAuthor());
        model.addAttribute("post", postService.findById(postId));
        model.addAttribute("comment", new Comment());
        return "post";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("post", new PostCreateDto(null, null, null));
        return "postCreateForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute(name = "post") PostCreateDto postCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "postCreateForm";
        }

        Post post = Post.builder()
                        .author(userService.findByUsername(postCreateDto.getUsername()))
                        .title(postCreateDto.getTitle())
                        .content(postCreateDto.getContent())
                        .build();

        Post savedPost = postRepository.save(post);
        return "redirect:/infoBoard/" + savedPost.getId();
    }

    @PostMapping("/{postId}/comments")
    public String createComment(@PathVariable Long postId,
                                @Valid @ModelAttribute("comment") CommentCreateDto commentCreateDto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postService.findById(postId));
            return "post";
        }

        commentService.create(commentCreateDto);
        return "redirect:/infoBoard/{postId}";
    }

    @GetMapping("/{postId}/update")
    public String postUpdateForm(@PathVariable Long postId, Model model) {
        model.addAttribute("post", postService.findById(postId));
        return "postUpdateForm";
    }

    @PostMapping("/{postId}/update")
    public String update(@PathVariable Long postId, @Valid @ModelAttribute("post") PostUpdateDto postUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "postUpdateForm";
        }
        Post post = postService.findById(postId);
        post.setTitle(postUpdateDto.getTitle());
        post.setContent(postUpdateDto.getContent());
        postRepository.save(post);
        return "redirect:/infoBoard/{postId}";
    }

    @PostMapping("/{postId}/delete")
    public String delete(@PathVariable Long postId) {
        postService.delete(postId);
        return "redirect:/infoBoard";
    }

    @PostMapping("/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return "redirect:/infoBoard/{postId}";
    }

    @PostMapping("/{postId}/comments/{commentId}/update")
    public String updateComment(@PathVariable Long postId,
                                @Valid @ModelAttribute("commentUpdateDto") CommentUpdateDto commentUpdateDto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            model.addAttribute("post", postService.findById(postId));
            model.addAttribute("comment", new Comment());
            return "post";
        }

        commentService.update(commentUpdateDto);
        return "redirect:/infoBoard/{postId}";
    }

}
