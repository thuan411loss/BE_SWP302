package vn.BE_SWP302.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TreatmentScheduleRequest {
    private Long resultId;
    private String startDate;
    private String endDate;
    private String status;
    private String notes;
}
