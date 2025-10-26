package com.nhnacademy.springbootnhnmart.config;

import com.nhnacademy.springbootnhnmart.interceptor.LoginCheckInterceptor;
import com.nhnacademy.springbootnhnmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserRepository userRepository;

    @Value("${upload.directory}")
    private String uploadDirectory;

    public WebConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor(userRepository))
                .addPathPatterns("/cs/**", "/")
                .excludePathPatterns("/cs/login" ,"/cs/login*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDirectory);
    }
}
