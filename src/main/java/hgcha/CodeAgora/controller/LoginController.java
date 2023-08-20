package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.domain.user.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "loginForm";
    }

}
