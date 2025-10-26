package com.nhnacademy.springbootnhnmart.controller;

import com.nhnacademy.springbootnhnmart.domain.AnswerRequest;
import com.nhnacademy.springbootnhnmart.domain.Inquiry;
import com.nhnacademy.springbootnhnmart.repository.InquiryRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cs/admin")
@RequiredArgsConstructor
public class AdminController {
    private final InquiryRepository inquiryRepository;

    // 어드민페이지 -> 답변 안달린 문의 목록 조회
    @GetMapping
    public String adminMain(Model model){
        model.addAttribute("inquiryList", inquiryRepository.findUnanswered());
        return "adminMain";
    }

    @PostMapping("/answer")
    public String doAnswer(@Valid @ModelAttribute("answerRequest") AnswerRequest answerRequest,
                           BindingResult bindingResult, HttpSession session, Model model){

        if (bindingResult.hasErrors()) {
            Inquiry inquiry = inquiryRepository.findById(answerRequest.getId());
            model.addAttribute("inquiry", inquiry);

            return "adminAnswerForm";
        }

        String adminId = (String) session.getAttribute("userId");

        inquiryRepository.updateAnswer(
                answerRequest.getId(),
                adminId,
                answerRequest.getAnswerContent()
        );

        return "redirect:/cs/admin";
    }

    @GetMapping("/answer")
    public String answerForm(@RequestParam Long id, Model model){
        Inquiry inquiry = inquiryRepository.findById(id);

        AnswerRequest answerRequest = new AnswerRequest();
        answerRequest.setId(inquiry.getId());

        model.addAttribute("inquiry", inquiry);
        model.addAttribute("answerRequest", answerRequest);

        return "adminAnswerForm";
    }
}
