package org.example.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project.common.reponse.ApiResponse;
import org.example.project.model.entity.Booking;
import org.example.project.model.entity.User;
import org.example.project.model.dto.RegisterRequestDto;
import org.example.project.service.UserService;
import org.example.project.service.impl.BookingServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    private final BookingServiceImpl bookingServiceImpl;

    @PreAuthorize("hasRole('ADMIN')")

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody RegisterRequestDto registerRequestDto) {
        User user=userService.repairUser(id,registerRequestDto);
        ApiResponse apiResponse =ApiResponse.builder().success(true).data(user).status(200).message("UPDATE SUCCESS").build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ApiResponse apiResponse =ApiResponse.builder().success(true).message("DELETE SUCCESS").build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> findAll(@RequestParam(name = "search",defaultValue = "") String search, @PageableDefault(size = 5,page = 0,sort ="fullName")Pageable pageable) {
        Page<User> listUser=userService.findAllBySearch(search,pageable);
        List <User> userList=listUser.getContent();
        ApiResponse apiResponse=ApiResponse.builder().success(true).data(userList).status(200).message("SUCCESS").build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/bookings/{id}/approve")
    public ResponseEntity<?> approveBooking(@PathVariable Long id) {

        Booking booking = bookingServiceImpl.approveBooking(id);

        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("BOOKING APPROVED")
                .data(booking)
                .build();

        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/bookings/{id}/reject")
    public ResponseEntity<?> rejectBooking(@PathVariable Long id) {

        Booking booking = bookingServiceImpl.rejectBooking(id);

        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("BOOKING REJECTED")
                .data(booking)
                .build();

        return ResponseEntity.ok(response);
    }
}
