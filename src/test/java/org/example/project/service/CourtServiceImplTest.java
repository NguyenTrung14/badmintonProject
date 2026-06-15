package org.example.project.service;

import org.example.project.exception.HttpNotFoundException;
import org.example.project.model.entity.Court;
import org.example.project.repository.CourtRepository;
import org.example.project.service.impl.CourtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourtServiceImplTest {

    @Mock
    private CourtRepository courtRepository;

    private CourtServiceImpl courtService;

    @BeforeEach
    void setUp() {
        courtService = new CourtServiceImpl(courtRepository);
    }

    @Test
    void updateCourtSavesAllImageUrls() {
        Court court = Court.builder().id(1L).build();
        List<String> imageUrls = List.of("https://cdn.test/one.jpg", "https://cdn.test/two.jpg");

        when(courtRepository.findById(1L)).thenReturn(Optional.of(court));
        when(courtRepository.save(court)).thenReturn(court);

        Court result = courtService.updateCourt(1L, imageUrls);

        assertSame(court, result);
        assertEquals(imageUrls, court.getImageUrls());
        verify(courtRepository).save(court);
    }

    @Test
    void updateCourtUsesFirstImageAsMainImage() {
        Court court = Court.builder().id(2L).build();
        List<String> imageUrls = List.of("https://cdn.test/main.jpg", "https://cdn.test/extra.jpg");

        when(courtRepository.findById(2L)).thenReturn(Optional.of(court));
        when(courtRepository.save(court)).thenReturn(court);

        courtService.updateCourt(2L, imageUrls);

        assertEquals("https://cdn.test/main.jpg", court.getImageUrl());
    }

    @Test
    void updateCourtClearsMainImageWhenImageListIsEmpty() {
        Court court = Court.builder()
                .id(3L)
                .imageUrl("https://cdn.test/old.jpg")
                .imageUrls(new ArrayList<>(List.of("https://cdn.test/old.jpg")))
                .build();

        when(courtRepository.findById(3L)).thenReturn(Optional.of(court));
        when(courtRepository.save(court)).thenReturn(court);

        courtService.updateCourt(3L, List.of());

        assertNull(court.getImageUrl());
        assertEquals(List.of(), court.getImageUrls());
    }

    @Test
    void updateCourtThrowsWhenCourtDoesNotExist() {
        when(courtRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(HttpNotFoundException.class, () -> courtService.updateCourt(99L, List.of("https://cdn.test/x.jpg")));

        verify(courtRepository, never()).save(any());
    }

    @Test
    void updateCourtCopiesInputImageListBeforeSaving() {
        Court court = Court.builder().id(4L).build();
        List<String> imageUrls = new ArrayList<>(List.of("https://cdn.test/original.jpg"));

        when(courtRepository.findById(4L)).thenReturn(Optional.of(court));
        when(courtRepository.save(court)).thenReturn(court);

        courtService.updateCourt(4L, imageUrls);
        imageUrls.add("https://cdn.test/changed-after-save.jpg");

        ArgumentCaptor<Court> courtCaptor = ArgumentCaptor.forClass(Court.class);
        verify(courtRepository).save(courtCaptor.capture());
        assertEquals(List.of("https://cdn.test/original.jpg"), courtCaptor.getValue().getImageUrls());
    }
}
