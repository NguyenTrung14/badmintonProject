package org.example.project.service;

import org.example.project.model.entity.Court;
import org.springframework.web.bind.annotation.RequestBody;

public interface CourtService {
    Court updateCourt(Long id,String url);
}
