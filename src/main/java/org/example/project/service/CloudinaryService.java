package org.example.project.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.example.project.exception.HttpUploadImageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );

            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new HttpUploadImageException("Upload failed");
        }
    }

    public List<String> uploadFiles(List<MultipartFile> files) {
        if (files == null) {
            throw new HttpUploadImageException("No image files selected");
        }

        List<MultipartFile> validFiles = files.stream()
                .filter(Objects::nonNull)
                .filter(file -> !file.isEmpty())
                .toList();

        if (validFiles.isEmpty()) {
            throw new HttpUploadImageException("No image files selected");
        }

        return validFiles.stream()
                .map(this::uploadFile)
                .toList();
    }
}
