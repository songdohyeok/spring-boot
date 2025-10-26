package com.nhnacademy.springbootnhnmart.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebControllerAdvice {
    @ExceptionHandler(Exception.class)
    public String handleAll(Exception e, Model model) {
        model.addAttribute("exception", e);
        return "error";
    }
}
