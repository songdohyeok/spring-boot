package com.nhnacademy.springbootnhnmart;

import com.nhnacademy.springbootnhnmart.controller.AdminController;
import com.nhnacademy.springbootnhnmart.domain.Inquiry;
import com.nhnacademy.springbootnhnmart.domain.User;
import com.nhnacademy.springbootnhnmart.repository.InquiryRepository;
import com.nhnacademy.springbootnhnmart.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    InquiryRepository inquiryRepository;

    @MockitoBean
    UserRepository userRepository;

    private User buildAdminUser() {
        User u = new User();
        u.setId("admin1");
        u.setPassword("123");
        u.setName("관리자");
        u.setAuth(User.Auth.ROLE_ADMIN);
        return u;
    }

    @Test
    void adminMainShowTest() throws Exception {
        when(userRepository.getUser("admin1")).thenReturn(buildAdminUser());
        when(inquiryRepository.findUnanswered()).thenReturn(List.of());

        mockMvc.perform(get("/cs/admin").sessionAttr("userId", "admin1"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminMain"))
                .andExpect(model().attributeExists("inquiryList"));
    }

    @Test
    void answerFormShowTest() throws Exception {
        when(userRepository.getUser("admin1")).thenReturn(buildAdminUser());

        Inquiry inquiry = new Inquiry();
        inquiry.setId(1L);
        inquiry.setTitle("t");
        inquiry.setContent("c");
        inquiry.setCategory(Inquiry.Category.COMPLAINTS);
        inquiry.setCustomerId("customer1");
        inquiry.setCreatedAt(LocalDateTime.now());

        when(inquiryRepository.findById(1L)).thenReturn(inquiry);

        mockMvc.perform(get("/cs/admin/answer").param("id", "1")
                        .sessionAttr("userId", "admin1"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminAnswerForm"))
                .andExpect(model().attributeExists("inquiry"))
                .andExpect(model().attributeExists("answerRequest"));
    }
}
