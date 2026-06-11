package org.example.project.service;

import org.example.project.model.entity.Booking;

import java.util.List;

public interface CustomerService {
    List<Booking> getBookingsByUser(Long UserId);
}
