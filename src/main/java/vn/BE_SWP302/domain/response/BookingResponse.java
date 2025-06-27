package vn.BE_SWP302.domain.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingResponse {
    private Long id;
    private String customerName;
    private String doctorName;
    private String serviceName;
    private LocalDateTime appointmentTime;
    private String status;
}
