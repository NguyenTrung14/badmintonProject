package org.example.project.service;

import org.example.project.model.entity.Court;

import java.util.List;

public interface CourtService {
    Court updateCourt(Long id, List<String> imageUrls);
}
