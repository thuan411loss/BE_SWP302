package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreatmentRecordRequest {

    private String diagnosis;
    private String name;
    private String treatmentPlan;
    private Long bookingId;
    private Long resultId;
}