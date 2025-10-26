package com.nhnacademy.springbootnhnmart.repository;

import com.nhnacademy.springbootnhnmart.domain.Inquiry;
import java.util.List;

public interface InquiryRepository {

    // 게시글 조회
    Inquiry findById(Long id);

    // 게시글 작성
    void save(String title, String content, String customerId, Inquiry.Category category, List<String> attachmentPaths);

    // 본인이 작성한 문의 리스트 (손님용)
    List<Inquiry> findByCustomerId(String customerId);

    // 답변되지 않은 문의 리스트 (어드민용)
    List<Inquiry> findUnanswered();

    // 답변하기
    void updateAnswer(Long id, String adminId, String answerContent);

}
