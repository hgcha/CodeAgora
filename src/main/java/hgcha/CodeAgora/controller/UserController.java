package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.dto.UserCreateDto;
import hgcha.CodeAgora.entity.User;
import hgcha.CodeAgora.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/create")
    public String joinForm(Model model) {
        model.addAttribute("user", new User());
        return "joinForm";
    }

    @PostMapping("/users/create")
    public String join(@Valid @ModelAttribute("user") UserCreateDto userCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "joinForm";
        }

        userService.create(userCreateDto);
        return "redirect:/infoBoard";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "loginForm";
    }
}
