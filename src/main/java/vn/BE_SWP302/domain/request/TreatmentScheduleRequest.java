package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class TreatmentScheduleRequest {

    private Long bookingId;
    private String stageName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<String> activities;
}
