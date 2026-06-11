package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.common.reponse.ApiResponse;
import org.example.project.model.entity.Booking;
import org.example.project.model.entity.dto.BookingRequestDto;
import org.example.project.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final BookingService bookingService;
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDto bookingDto) {
        Booking booking = bookingService.createBooking(bookingDto);
        ApiResponse apiResponse = ApiResponse.builder().
                status(201)
                .success(true)
                .data(booking).message("Booking Created").build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
