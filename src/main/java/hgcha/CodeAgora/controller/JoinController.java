package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.domain.user.dto.UserCreateDto;
import hgcha.CodeAgora.domain.user.entity.User;
import hgcha.CodeAgora.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/join")
public class JoinController {

    private final UserService userService;

    @GetMapping
    public String joinForm(Model model) {
        model.addAttribute("user", new User());
        return "joinForm";
    }

    @PostMapping
    public String join(@Valid @ModelAttribute("user") UserCreateDto userCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "joinForm";
        }

        userService.create(userCreateDto);
        return "redirect:/info";
    }

}
