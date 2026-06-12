package org.example.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.project.exception.HttpNotFoundException;
import org.example.project.model.entity.Court;
import org.example.project.repository.CourtRepository;
import org.example.project.service.CourtService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {
    private final CourtRepository courtRepository;
    @Override
    public Court updateCourt(Long id, List<String> imageUrls) {
        Court court = courtRepository.findById(id).orElseThrow(()->new HttpNotFoundException("Court not found"));
        court.setImageUrls(new ArrayList<>(imageUrls));
        court.setImageUrl(imageUrls.isEmpty() ? null : imageUrls.get(0));
        return courtRepository.save(court);
    }
}
