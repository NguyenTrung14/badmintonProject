package org.example.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project.common.reponse.ApiResponse;
import org.example.project.model.entity.User;
import org.example.project.model.entity.dto.LoginRequestDto;
import org.example.project.model.entity.dto.RegisterRequestDto;
import org.example.project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        User user = userService.registerUser(registerRequestDto);
        ApiResponse apiResponse =ApiResponse.builder().success(true).data(user).status(201).message("CREATE SUCCESS").build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        String accessToken=userService.login(loginRequestDto);
        ApiResponse apiResponse=ApiResponse.builder().success(true).data(accessToken).status(200).message("LOGIN SUCCESS").build();
        return ResponseEntity.ok(apiResponse);
    }
}
