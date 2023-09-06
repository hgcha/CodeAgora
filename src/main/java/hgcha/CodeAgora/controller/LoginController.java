package hgcha.CodeAgora.controller;

import hgcha.CodeAgora.domain.user.entity.User;
import hgcha.CodeAgora.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String username,
                            Model model) {
        if (error != null) {
            if (error.equals("locked")) {
                User user = userService.findByUsername(username);
                model.addAttribute("errorInfo",
        "해당 계정은 "
                + user.getBannedUntil().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                + "까지 이용 정지되었습니다.");
            }
        }

        model.addAttribute("user", new User());
        return "loginForm";
    }

}
