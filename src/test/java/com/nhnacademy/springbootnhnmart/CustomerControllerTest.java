package com.nhnacademy.springbootnhnmart;

import com.nhnacademy.springbootnhnmart.controller.CustomerController;
import com.nhnacademy.springbootnhnmart.domain.User;
import com.nhnacademy.springbootnhnmart.repository.InquiryRepository;
import com.nhnacademy.springbootnhnmart.repository.UserRepository;
import com.nhnacademy.springbootnhnmart.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    InquiryRepository inquiryRepository;

    @MockitoBean
    FileService fileService;

    @MockitoBean
    UserRepository userRepository;

    private User buildCustomerUser() {
        User u = new User();
        u.setId("customer1");
        u.setPassword("123");
        u.setName("고객");
        u.setAuth(User.Auth.ROLE_CUSTOMER);
        return u;
    }

    @Test
    void customerMainShowTest() throws Exception {
        when(userRepository.getUser("customer1"))
                .thenReturn(buildCustomerUser());

        when(inquiryRepository.findByCustomerId("customer1"))
                .thenReturn(List.of());

        mockMvc.perform(get("/cs").sessionAttr("userId", "customer1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerMain"))
                .andExpect(model().attributeExists("inquiryList"));
    }

    @Test
    void inquiryFormShowTest() throws Exception {
        when(userRepository.getUser("customer1"))
                .thenReturn(buildCustomerUser());

        mockMvc.perform(get("/cs/inquiry").sessionAttr("userId", "customer1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerInquiryForm"))
                .andExpect(model().attributeExists("inquiryRequest"));
    }
}
