package vn.BE_SWP302.domain.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionRequest {
    private Long treatmentRecordId;
    private String medicineName;
    private String dosage;
    private String instructions;
    private LocalDate prescribedDate; // Ngày kê đơn
    private String frequency; // Tần suất
    private String duration; // Thời gian
}
