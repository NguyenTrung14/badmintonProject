package org.example.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project.common.enumType.reponse.ApiResponse;
import org.example.project.model.entity.User;
import org.example.project.model.entity.dto.RegisterRequestDto;
import org.example.project.repository.UserRepository;
import org.example.project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody RegisterRequestDto registerRequestDto) {
        User user=userService.registerUser(registerRequestDto);
        ApiResponse apiResponse =ApiResponse.builder().success(true).data(user).status(200).message("UPDATE SUCCESS").build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ApiResponse apiResponse =ApiResponse.builder().success(true).message("DELETE SUCCESS").build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @GetMapping("/list")
    public ResponseEntity<?> findAll(@RequestParam(name = "search",defaultValue = "") String search, @PageableDefault(size = 5,page = 0,sort ="fullName")Pageable pageable) {
        Page<User> listUser=userService.findAllBySearch(search,pageable);
        List <User> userList=listUser.getContent();
        ApiResponse apiResponse=ApiResponse.builder().success(true).data(userList).status(200).message("SUCCESS").build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
