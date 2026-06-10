package org.example.project.service;

import org.example.project.model.entity.Booking;
import org.example.project.model.entity.dto.BookingRequestDto;

public interface BookingService {
    Booking createBooking(BookingRequestDto bookingDto);
}
