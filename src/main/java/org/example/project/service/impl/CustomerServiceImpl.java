package org.example.project.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.project.model.entity.Booking;
import org.example.project.repository.BookingRepository;
import org.example.project.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final BookingRepository bookingRepository;
    @Override
    public List<Booking> getBookingsByUser(Long UserId) {
        return bookingRepository.findByUserId(UserId);
    }
}
