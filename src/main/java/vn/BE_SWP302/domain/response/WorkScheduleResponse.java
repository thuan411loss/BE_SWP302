package vn.BE_SWP302.domain.response;

import lombok.Setter;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Setter
public class WorkScheduleResponse {
    private Long scheduleId;
    private Long userId;
    private String doctorName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isAvailable;
    private String notes;
}
