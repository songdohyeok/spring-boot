package com.nhnacademy.springbootnhnmart.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    public List<String> saveFiles(List<MultipartFile> uploadFiles) {
        List<String> attachmentPaths = new ArrayList<>();

        for(MultipartFile file : uploadFiles) {
            if (!file.isEmpty()) {
                try {
                    String originalName = file.getOriginalFilename();

                    // 원본 파일명에서 경로 관련 문자 모두 제거 (디렉토리 경로를 모두 제거해야 path traversal 공격을 막는다고 함)
                    String filename = StringUtils.getFilename(originalName);

                    // 확장자가 없거나 이름이 유효하지 않을시 파일 저장 안함
                    if (filename == null || filename.lastIndexOf(".") == -1) {
                        continue;
                    }

                    // extension에 저장된 확장자가 조건에 맞지 않으면 저장 안함
                    String extension = filename.substring(filename.lastIndexOf("."));
                    List<String> extensions = List.of(".png", ".jpg", ".jpeg", ".gif");

                    if (!extensions.contains(extension.toLowerCase())) {
                        continue;
                    }

                    // 랜덤이름.확장자 -> 파일의 중복을 막는다
                    String savedName = UUID.randomUUID() + extension;
                    Path savePath = Paths.get(uploadDirectory, savedName);

                    file.transferTo(savePath);
                    attachmentPaths.add(savedName);

                } catch (IOException | IllegalStateException e) {
                    throw new RuntimeException("파일 저장 실패", e);
                }
            }
        }
        return attachmentPaths;
    }
}
