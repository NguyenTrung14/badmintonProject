package org.example.project.controller;

import org.example.project.exception.GlobalHandlerException;
import org.example.project.exception.HttpUploadImageException;
import org.example.project.model.entity.Court;
import org.example.project.service.CloudinaryService;
import org.example.project.service.CourtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ManagerControllerTest {

    private CourtService courtService;
    private CloudinaryService cloudinaryService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        courtService = mock(CourtService.class);
        cloudinaryService = mock(CloudinaryService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ManagerController(courtService, cloudinaryService))
                .setControllerAdvice(new GlobalHandlerException())
                .build();
    }

    @Test
    void updateCourtAcceptsMultipleFilesPart() throws Exception {
        MockMultipartFile firstImage = image("files", "first.jpg");
        MockMultipartFile secondImage = image("files", "second.jpg");
        List<String> imageUrls = List.of("https://cdn.test/first.jpg", "https://cdn.test/second.jpg");
        Court court = courtWithImages(1L, imageUrls);

        when(cloudinaryService.uploadFiles(anyList())).thenReturn(imageUrls);
        when(courtService.updateCourt(1L, imageUrls)).thenReturn(court);

        mockMvc.perform(multipart("/api/v1/manager/court/{id}", 1L)
                        .file(firstImage)
                        .file(secondImage)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.imageUrl").value("https://cdn.test/first.jpg"))
                .andExpect(jsonPath("$.imageUrls", hasSize(2)));

        verify(cloudinaryService).uploadFiles(anyList());
        verify(courtService).updateCourt(1L, imageUrls);
    }

    @Test
    void updateCourtAcceptsLegacyFilePart() throws Exception {
        MockMultipartFile image = image("file", "legacy.jpg");
        List<String> imageUrls = List.of("https://cdn.test/legacy.jpg");
        Court court = courtWithImages(2L, imageUrls);

        when(cloudinaryService.uploadFiles(anyList())).thenReturn(imageUrls);
        when(courtService.updateCourt(2L, imageUrls)).thenReturn(court);

        mockMvc.perform(multipart("/api/v1/manager/court/{id}", 2L)
                        .file(image)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imageUrls[0]").value("https://cdn.test/legacy.jpg"));

        verify(courtService).updateCourt(2L, imageUrls);
    }

    @Test
    void updateCourtCombinesFilesAndLegacyFileParts() throws Exception {
        MockMultipartFile newImage = image("files", "new.jpg");
        MockMultipartFile legacyImage = image("file", "legacy.jpg");
        List<String> imageUrls = List.of("https://cdn.test/new.jpg", "https://cdn.test/legacy.jpg");
        Court court = courtWithImages(3L, imageUrls);

        when(cloudinaryService.uploadFiles(anyList())).thenReturn(imageUrls);
        when(courtService.updateCourt(3L, imageUrls)).thenReturn(court);

        mockMvc.perform(multipart("/api/v1/manager/court/{id}", 3L)
                        .file(newImage)
                        .file(legacyImage)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imageUrls", hasSize(2)));

        verify(cloudinaryService).uploadFiles(anyList());
        verify(courtService).updateCourt(3L, imageUrls);
    }

    @Test
    void updateCourtReturnsBadRequestWhenUploadServiceRejectsFiles() throws Exception {
        when(cloudinaryService.uploadFiles(anyList())).thenThrow(new HttpUploadImageException("No image files selected"));

        mockMvc.perform(multipart("/api/v1/manager/court/{id}", 4L)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("No image files selected"));

        verify(courtService, never()).updateCourt(eq(4L), anyList());
    }

    @Test
    void updateCourtRejectsJsonRequestBody() throws Exception {
        mockMvc.perform(put("/api/v1/manager/court/{id}", 5L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnsupportedMediaType());

        verify(cloudinaryService, never()).uploadFiles(anyList());
        verify(courtService, never()).updateCourt(eq(5L), anyList());
    }

    private MockMultipartFile image(String partName, String fileName) {
        return new MockMultipartFile(
                partName,
                fileName,
                MediaType.IMAGE_JPEG_VALUE,
                "image-content".getBytes(StandardCharsets.UTF_8)
        );
    }

    private Court courtWithImages(Long id, List<String> imageUrls) {
        return Court.builder()
                .id(id)
                .imageUrl(imageUrls.get(0))
                .imageUrls(new ArrayList<>(imageUrls))
                .build();
    }
}
