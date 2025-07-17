package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class TreatmentScheduleRequest {
    // private Long resultId; // Không cần truyền từ client nữa, sẽ lấy tự động theo
    // customerId
    private Long customerId;
    private String stageName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<String> activities;
}
