package org.example.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.project.exception.HttpNotFoundException;
import org.example.project.model.entity.Court;
import org.example.project.repository.CourtRepository;
import org.example.project.service.CourtService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourtServiceImpl implements CourtService {
    private final CourtRepository courtRepository;
    @Override
    public Court updateCourt(Long id, String url) {
        Court court = courtRepository.findById(id).orElseThrow(()->new HttpNotFoundException("Court not found"));
        court.setImageUrl(url);
        return courtRepository.save(court);
    }
}
