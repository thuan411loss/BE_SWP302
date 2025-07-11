package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ExaminationResponse {
    private Long examId;
    private String name;
    private Long bookingId;
    private LocalDateTime examDate;
    private String diagnosis;
    private String recommendation;

    private String normalRange;

    // Thông tin booking (nếu cần)
    private String customerName;
    private String doctorName;
    private String serviceName;
}