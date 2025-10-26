package com.nhnacademy.springbootnhnmart.controller;

import com.nhnacademy.springbootnhnmart.domain.User;
import com.nhnacademy.springbootnhnmart.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cs")
@RequiredArgsConstructor
public class UserLoginController {
    private final UserRepository userRepository;

    @PostMapping("/login")
    public String doLogin(@RequestParam("id") String id, @RequestParam("pw") String pw,
                          HttpServletRequest request, Model model){
        if(userRepository.matches(id, pw)){
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", id);

            User userAuth = userRepository.getUser(id);

            switch (userAuth.getAuth()){
                case ROLE_ADMIN -> {
                    return "redirect:/cs/admin";
                }
                case ROLE_CUSTOMER -> {
                    return "redirect:/cs";
                }
                default -> {
                    model.addAttribute("error", "권한이 올바르지 않습니다");
                    return "loginForm";
                }
            }
        }
        model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다");
        return "loginForm";
    }

    @GetMapping("/login")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/cs/login";
    }
}
