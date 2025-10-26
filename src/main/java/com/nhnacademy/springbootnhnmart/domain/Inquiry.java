package com.nhnacademy.springbootnhnmart.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inquiry {
    public enum Category{
        COMPLAINTS("불만 접수"),
        SUGGESTIONS("제안"),
        RETURNS_EXCHANGES("환불/교환"),
        COMPLIMENTS("칭찬해요"),
        GENERAL_INQUIRY("기타 문의");

        @Getter
        private final String label;

        Category(String label) {
            this.label = label;
        }
    }
    // 문의
    private Long id; // 게시글 id -> 주소 매핑 등에 사용
    private String title;
    private String content;
    private String customerId;
    private Category category;
    private LocalDateTime createdAt;
    private List<String> attachmentPaths;

    // 답변
    private String answerContent;
    private String answerAdminId;
    private LocalDateTime answeredAt;

}
