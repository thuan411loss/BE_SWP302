package vn.BE_SWP302.domain.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicalResultsRequest {
    private Long examId;
    private String resultValue;
    private LocalDate resultDate;
    private String conclusion;
}
