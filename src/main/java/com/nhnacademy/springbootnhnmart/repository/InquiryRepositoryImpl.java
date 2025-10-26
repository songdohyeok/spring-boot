package com.nhnacademy.springbootnhnmart.repository;

import com.nhnacademy.springbootnhnmart.domain.Inquiry;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class InquiryRepositoryImpl implements InquiryRepository {
    private final Map<Long, Inquiry> inquiryMap = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    @Override
    public Inquiry findById(Long id) {
        return inquiryMap.get(id);
    }

    @Override
    public void save(String title, String content, String customerId, Inquiry.Category category, List<String> attachmentPaths) {
        Inquiry inquiry = new Inquiry();
        inquiry.setId(counter.incrementAndGet());
        inquiry.setTitle(title);
        inquiry.setContent(content);
        inquiry.setCustomerId(customerId);
        inquiry.setCategory(category);
        inquiry.setCreatedAt(LocalDateTime.now());
        inquiry.setAttachmentPaths(attachmentPaths);

        inquiryMap.put(inquiry.getId(), inquiry);

    }

    @Override
    public List<Inquiry> findByCustomerId(String customerId) {
        return  inquiryMap.values().stream()
                .filter(inquiry -> inquiry.getCustomerId().equals(customerId))
                .sorted(Comparator.comparing(Inquiry::getCreatedAt).reversed())
                .toList();
    }

    @Override
    public List<Inquiry> findUnanswered() {
        return inquiryMap.values().stream()
                .filter(inquiry -> inquiry.getAnswerContent() == null)
                .toList();
    }

    @Override
    public void updateAnswer(Long id, String adminId, String answerContent) {
        Inquiry inquiry = inquiryMap.get(id);
        if(inquiry != null && inquiry.getAnswerContent() == null){
            inquiry.setAnswerAdminId(adminId);
            inquiry.setAnswerContent(answerContent);
            inquiry.setAnsweredAt(LocalDateTime.now());
        }
    }
}
