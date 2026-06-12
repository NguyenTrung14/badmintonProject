package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.service.CloudinaryService;
import org.example.project.service.CourtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/manager")
public class ManagerController {
    private final CourtService courtService;
    private final CloudinaryService cloudinaryService;
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/court/{id}")
    public ResponseEntity<?> updateCourt(@RequestPart MultipartFile file, @PathVariable Long id) {
        String imageUrl = cloudinaryService.uploadFile(file);
        return ResponseEntity.ok(courtService.updateCourt(id, imageUrl));
    }
}
