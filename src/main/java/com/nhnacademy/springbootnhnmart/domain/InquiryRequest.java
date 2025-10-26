package com.nhnacademy.springbootnhnmart.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryRequest {

    @NotBlank(message = "제목을 입력해주세요")
    @Size(min = 2, max = 200, message = "제목은 2자 이상 200자 이하만 가능합니다")
    private String title;

    @NotBlank(message = "내용을 입력해주세요")
    @Size(max = 40000, message = "본문은 40000자 이하만 가능합니다")
    private String content;

    @NotNull(message = "카테고리를 선택해주세요")
    private Inquiry.Category category;
}