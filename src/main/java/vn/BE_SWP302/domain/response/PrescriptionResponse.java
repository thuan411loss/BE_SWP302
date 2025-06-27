package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionResponse {
    private Long id;
    private String medicineName;
    private String dosage;
    private String instructions;
}
