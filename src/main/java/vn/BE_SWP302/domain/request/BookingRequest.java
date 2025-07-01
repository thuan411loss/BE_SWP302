package vn.BE_SWP302.domain.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    private Long doctorId;
    private Long serviceId;
    private LocalDateTime appointmentTime;
    private String note;
}