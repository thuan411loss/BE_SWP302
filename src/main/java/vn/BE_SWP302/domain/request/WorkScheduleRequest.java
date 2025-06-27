package vn.BE_SWP302.domain.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import vn.BE_SWP302.domain.User;

@Getter
@Setter
public class WorkScheduleRequest {
    private Long doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String notes;
}
