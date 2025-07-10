package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TreatmentScheduleResponse {
    private Long scheduleId;
    private String stageName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<String> activities;

}