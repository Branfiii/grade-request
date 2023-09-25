package mk.ukim.finki.wp.baranjabackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

    @RequestMapping("/login")
    public String loginForm() {
        return "login";
    }
}
