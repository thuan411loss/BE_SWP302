package vn.BE_SWP302.domain.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionRequest {
    private String medicineName;
    private String dosage;
    private String frequency;
    private String duration;
    private String instruction;
    private LocalDate prescribedDate;
    private Long treatmentScheduleId;
}

