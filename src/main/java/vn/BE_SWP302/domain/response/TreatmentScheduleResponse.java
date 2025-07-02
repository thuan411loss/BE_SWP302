package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TreatmentScheduleResponse {
    private Long scheduleId;
    private Long medicalResultId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String notes;
}