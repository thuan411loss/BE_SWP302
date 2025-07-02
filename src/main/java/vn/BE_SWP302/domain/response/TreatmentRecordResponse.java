package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TreatmentRecordResponse {
    private Long id;
    private String diagnosis;
    private String treatmentPlan;
    private LocalDateTime createdDate;
}