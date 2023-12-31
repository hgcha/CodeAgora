package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.domain.comment.service.CommentService;
import hgcha.CodeAgora.domain.post.service.PostService;
import hgcha.CodeAgora.domain.user.dto.ChangePasswordDto;
import hgcha.CodeAgora.domain.user.entity.User;
import hgcha.CodeAgora.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public String profile(@AuthenticationPrincipal(expression = "user") User user, Model model) {
        model.addAttribute("posts", postService.getFiveRecentPosts(user));
        model.addAttribute("comments", commentService.findFiveRecentComments(user));
        return "profile";
    }

    @GetMapping("/change-password")
    public String changePasswordForm(@RequestParam(required = false) String result, Model model) {
        model.addAttribute("result", result);
        model.addAttribute("changePasswordDto", new ChangePasswordDto(null, null, null));
        return "changePasswordForm";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid ChangePasswordDto changePasswordDto,
                                 BindingResult bindingResult,
                                 @AuthenticationPrincipal(expression = "user") User user,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "changePasswordForm";
        }

        if (!bCryptPasswordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
            bindingResult.addError(new FieldError(
                    "changePasswordDto",
                    "currentPassword",
                    "현재 비밀번호에 엡력된 값이 올바르지 않습니다."));

            return "changePasswordForm";
        }

        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getNewPasswordForCheck())) {
            bindingResult.addError(new FieldError(
                    "changePasswordDto",
                    "newPassword",
                    "새 비밀번호와 비밀번호 확인에 입력된 값이 일치하지 않습니다."));

            return "changePasswordForm";
        }

        userService.changePassword(user, changePasswordDto.getNewPassword());

        redirectAttributes.addAttribute("result", "success");
        return "redirect:/profile/change-password";
    }

}
