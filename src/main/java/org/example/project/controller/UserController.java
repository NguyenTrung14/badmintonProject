package org.example.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project.common.reponse.ApiResponse;
import org.example.project.common.reponse.AuthResponse;
import org.example.project.model.dto.*;
import org.example.project.model.entity.User;
import org.example.project.securiry.principal.UserPrincipal;
import org.example.project.service.CloudinaryService;
import org.example.project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


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
        AuthResponse authResponse =userService.login(loginRequestDto);
        ApiResponse apiResponse=ApiResponse.builder().success(true).data(authResponse).status(200).message("LOGIN SUCCESS").build();
        return ResponseEntity.ok(apiResponse);
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/refresh")
    public AuthResponse refresh(@Valid @RequestBody TokenRequestDto req) {
        return userService.refreshToken(req.getRefreshToken());
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        userService.Logout(request);
        ApiResponse apiResponse = ApiResponse.builder().success(true).message("LOGOUT SUCCESS").build();
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody ForgotPasswordDto dto){

       String token= userService.forgotPasswordByEmail(dto.getEmail());

        return ResponseEntity.ok(token);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordDto dto){

        userService.resetPassword(dto);

        return ResponseEntity.ok("Password changed");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody ChangePasswordDto dto,
            @AuthenticationPrincipal UserPrincipal principal) {
        String username = principal.getUsername();
        userService.changePassword(username, dto);
        ApiResponse apiResponse = ApiResponse.builder().success(true).message("Password changed successfully").build();
        return ResponseEntity.ok(apiResponse);
    }

}
