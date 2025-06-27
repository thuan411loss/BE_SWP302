package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreatmentRecordRequest {
    private Long bookingId;
    private String diagnosis;
    private String treatmentPlan;
}