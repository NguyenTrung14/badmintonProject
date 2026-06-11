package org.example.project.model.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingRequestDto {
    @NotBlank(message = "Khung giờ không được để trống")
    @Pattern(
            regexp = "^([01]\\d|2[0-3]):[0-5]\\d-([01]\\d|2[0-3]):[0-5]\\d$",
            message = "Khung giờ phải có dạng HH:mm-HH:mm"
    )
    private String timeSlot;
    @NotNull(message = "Ngày đặt sân không được để trống")
    @FutureOrPresent(message = "Ngày đặt sân phải từ hôm nay trở đi")
    private LocalDate bookingDate;
    @NotNull(message = "User không được để trống")
    @Positive(message = "UserId phải lớn hơn 0")
    private Long userId;
    @NotNull(message = "Sân không được để trống")
    @Positive(message = "CourtId phải lớn hơn 0")
    private Long courtId;
}
