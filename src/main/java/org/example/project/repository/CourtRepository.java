package org.example.project.repository;

import org.example.project.model.entity.Court;
import org.springframework.data.repository.CrudRepository;

public interface CourtRepository extends CrudRepository<Court, Long> {
}
