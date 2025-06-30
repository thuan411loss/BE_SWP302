package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionRequest {
    private Long treatmentRecordId;
    private String medicineName;
    private String dosage;
    private String instructions;
}
