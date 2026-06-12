package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.service.CloudinaryService;
import org.example.project.service.CourtService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/manager")
public class ManagerController {
    private final CourtService courtService;
    private final CloudinaryService cloudinaryService;
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping(value = "/court/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateCourt(
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestPart(value = "file", required = false) List<MultipartFile> file,
            @PathVariable Long id
    ) {
        List<MultipartFile> uploadFiles = new ArrayList<>();
        if (files != null) {
            uploadFiles.addAll(files);
        }
        if (file != null) {
            uploadFiles.addAll(file);
        }

        List<String> imageUrls = cloudinaryService.uploadFiles(uploadFiles);
        return ResponseEntity.ok(courtService.updateCourt(id, imageUrls));
    }
}
