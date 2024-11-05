package com.todoslave.feedme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//웹 페이지 로그인 테스트용
@Controller
public class UserViewController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/testsite")
    public String testsite() {
        return "testsite";
    }

}
