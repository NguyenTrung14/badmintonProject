package org.example.project.service;

import org.example.project.model.entity.User;
import org.example.project.model.entity.dto.LoginRequestDto;
import org.example.project.model.entity.dto.RegisterRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    User registerUser(RegisterRequestDto registerRequestDto);
    User RepairUser(Long id,RegisterRequestDto registerRequestDto);
    Void deleteUser(Long id);
    Page<User> findAllBySearch(String search, Pageable pageable);
    String login(LoginRequestDto loginRequestDto);
}
