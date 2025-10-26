package com.nhnacademy.springbootnhnmart.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequest {

    private Long id;

    @NotBlank(message = "답변 내용을 입력해주세요")
    @Size(min = 1, max = 40000, message = "답변은 1자 이상 40000자 이하만 가능합니다")
    private String answerContent;
}