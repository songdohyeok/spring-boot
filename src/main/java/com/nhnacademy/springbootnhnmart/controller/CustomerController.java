package com.nhnacademy.springbootnhnmart.controller;

import com.nhnacademy.springbootnhnmart.domain.Inquiry;
import com.nhnacademy.springbootnhnmart.domain.InquiryRequest;
import com.nhnacademy.springbootnhnmart.repository.InquiryRepository;
import com.nhnacademy.springbootnhnmart.service.FileService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/cs")
@RequiredArgsConstructor
public class CustomerController {
    private final InquiryRepository inquiryRepository;

    private final FileService fileService;

    // 고객페이지 -> 내가 작성한 문의 목록만 조회
    @GetMapping
    public String customerMain(HttpSession session, Model model,
                               @RequestParam(required = false) Inquiry.Category category){
        String customerId = (String) session.getAttribute("userId");
        List<Inquiry> inquiryList = inquiryRepository.findByCustomerId(customerId);

        if (category != null) {
            inquiryList = inquiryList.stream()
                    .filter(inquiry -> inquiry.getCategory() == category)
                    .toList();
        }

        model.addAttribute("inquiryList", inquiryList);
        return "customerMain";
    }


    @PostMapping("/inquiry")
    public String doInquiry(@Valid InquiryRequest inquiryRequest,
                            BindingResult bindingResult,
                            @RequestParam("attachments") List<MultipartFile> uploadFiles,
                            HttpSession session, Model model) {
        String customerId = (String) session.getAttribute("userId");

        if (bindingResult.hasErrors()) {
            model.addAttribute("inquiryRequest", inquiryRequest);
            return "customerInquiryForm";
        }

        List<String> attachmentPaths = fileService.saveFiles(uploadFiles);

        inquiryRepository.save(inquiryRequest.getTitle(),
                inquiryRequest.getContent(), customerId,
                inquiryRequest.getCategory(), attachmentPaths);

        return "redirect:/cs";
    }

    @GetMapping("/inquiry")
    public String inquiryForm(Model model){
        model.addAttribute("inquiryRequest", new InquiryRequest());
        return "customerInquiryForm";
    }

    @GetMapping("/inquiry/{id}")
    public String inquiryDetail(@PathVariable Long id, Model model){
        Inquiry inquiry = inquiryRepository.findById(id);
        model.addAttribute("inquiry", inquiry);
        return "customerInquiryDetail";
    }
}
