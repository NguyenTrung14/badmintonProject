package org.example.project.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project.common.reponse.ApiResponse;
import org.example.project.model.entity.Booking;
import org.example.project.model.dto.BookingRequestDto;
import org.example.project.securiry.principal.UserPrincipal;
import org.example.project.service.BookingService;
import org.example.project.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final BookingService bookingService;
    private final CustomerService customerService;
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequestDto bookingDto) {
        Booking booking = bookingService.createBooking(bookingDto);
        ApiResponse apiResponse = ApiResponse.builder().
                status(201)
                .success(true)
                .data(booking).message("Booking Created").build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/bookings/history")
    public ResponseEntity<?> getBookingHistory(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        Long userId = userPrincipal.getUser().getId();

        List<Booking> bookings = customerService.getBookingsByUser(userId);

        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("GET BOOKING HISTORY SUCCESS")
                .data(bookings)
                .build();

        return ResponseEntity.ok(response);
    }
}
