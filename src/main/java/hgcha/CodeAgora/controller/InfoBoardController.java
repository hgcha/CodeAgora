package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.auth.PrincipalDetails;
import hgcha.CodeAgora.domain.comment.dto.CommentCreateDto;
import hgcha.CodeAgora.domain.comment.dto.CommentUpdateDto;
import hgcha.CodeAgora.domain.comment.dto.CommentVoteDto;
import hgcha.CodeAgora.domain.comment.entity.Comment;
import hgcha.CodeAgora.domain.comment.service.CommentService;
import hgcha.CodeAgora.domain.post.dto.PostCreateDto;
import hgcha.CodeAgora.domain.post.dto.PostUpdateDto;
import hgcha.CodeAgora.domain.post.dto.SearchConditionDto;
import hgcha.CodeAgora.domain.post.entity.Post;
import hgcha.CodeAgora.domain.post.service.PostService;
import hgcha.CodeAgora.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 정보게시판 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/info")
@Slf4j
public class InfoBoardController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public String list(HttpServletRequest request,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       Model model) {
        model.addAttribute("posts", postService.findPosts(page, size));
        model.addAttribute("requestURI", request.getRequestURI());
        return "posts";
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Post post = postService.findPostDetails(postId);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        if (principalDetails != null) {
            model.addAttribute("isLiked", post.getLikes().stream()
                                              .anyMatch(like -> like.getUser().getId() == principalDetails.getUser().getId()));
        }
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
            return "postCreateForm";
        }

        Post newPost = postService.createPost(postCreateDto);
        return "redirect:/info/" + newPost.getId();
    }

    @GetMapping("/{postId}/update")
    public String postUpdateForm(@PathVariable Long postId, Model model) {
        model.addAttribute("post", postService.findById(postId));
        return "postUpdateForm";
    }

    @PostMapping("/{postId}/update")
    public String update(@Valid @ModelAttribute("post") PostUpdateDto postUpdateDto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "postUpdateForm";
        }

        postService.updatePost(postUpdateDto);
        return "redirect:/info/{postId}";
    }

    @PostMapping("/{postId}/delete")
    public String delete(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/info";
    }

    @PostMapping("/{postId}/comments/create")
    public String createComment(@Valid @ModelAttribute("comment") CommentCreateDto commentCreateDto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postService.findById(commentCreateDto.getPostId()));
            return "post";
        }

        commentService.create(commentCreateDto);
        return "redirect:/info/{postId}";
    }

    @PostMapping("/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/info/{postId}";
    }

    @PostMapping("/{postId}/comments/{commentId}/update")
    public String updateComment(@PathVariable Long postId,
                                @Valid @ModelAttribute("commentUpdateDto") CommentUpdateDto commentUpdateDto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postService.findById(postId));
            model.addAttribute("comment", new Comment());
            return "post";
        }

        commentService.updateComment(commentUpdateDto);
        return "redirect:/info/{postId}";
    }

    @GetMapping("/search")
    public String search(HttpServletRequest request,
                         SearchConditionDto searchConditionDto,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "10") Integer size,
                         Model model) {
        model.addAttribute("requestURI", getRequestURIForSearch(request, searchConditionDto));

        String[] parsedResult = searchConditionDto.getSubject().split("_");
        if (parsedResult[0].equals("post")) {
            model.addAttribute("posts", postService.findAllBySubjectAndKeyword(searchConditionDto, page - 1, size));
            return "posts";
        } else if(parsedResult[0].equals("comment")) {
            model.addAttribute("comments", commentService.findAllBySubjectAndKeyword(searchConditionDto, page - 1, size));
            return "comments";
        } else {
            throw new IllegalArgumentException("잘못된 검색 쿼리입니다.");
        }
    }

    private String getRequestURIForSearch(HttpServletRequest request, SearchConditionDto searchConditionDto) {
        return request.getRequestURI()
                + "?subject=" + searchConditionDto.getSubject()
                + "&keyword=" + searchConditionDto.getKeyword();
    }

    @PostMapping("/{postId}/vote")
    public String votePost(@PathVariable Long postId, @AuthenticationPrincipal(expression = "user") User user) {
        postService.vote(postId, user);
        return "redirect:/info/{postId}";
    }

    @PostMapping("/{postId}/cancelVote")
    public String cancelPost(@PathVariable Long postId, @AuthenticationPrincipal(expression = "user") User user) {
        postService.cancelVote(postId, user);
        return "redirect:/info/{postId}";
    }

    @PostMapping("/{postId}/comments/{commentId}/vote")
    public String voteComment(@PathVariable Long postId, CommentVoteDto commentVoteDto) {
        commentService.voteComment(commentVoteDto);
        return "redirect:/info/{postId}";
    }

}
