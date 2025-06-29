package vn.BE_SWP302.domain.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkScheduleRequest {
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String notes;
}
