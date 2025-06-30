package vn.BE_SWP302.domain.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExaminationRequest {
    @NotNull(message = "Booking ID không được để trống")
    private Long bookingId;

    private LocalDateTime examDate;

    @NotBlank(message = "Chẩn đoán không được để trống")
    private String diagnosis;

    private String recommendation;
}
