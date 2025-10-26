package com.nhnacademy.springbootnhnmart;

import jakarta.annotation.PostConstruct;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootNhNmartApplication {

    @Value("${upload.directory}")
    private String uploadDirectory;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNhNmartApplication.class, args);
    }

    @PostConstruct
    public void init() {
        File directory = new File(uploadDirectory);
        if (!directory.exists()) {
            System.out.println("업로드 디렉토리 생성 "+uploadDirectory);
            directory.mkdirs();
        }
    }

}
