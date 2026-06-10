package org.example.project.repository;

import org.example.project.model.entity.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    @Query("select b from Booking b where b.court.id=:courtId and b.bookingDate=:bookingDate")
    List<Booking> findByCourtIdAndBookingDate(@Param("courtId") Long courtId,@Param("bookingDate") LocalDate bookingDate);
}
