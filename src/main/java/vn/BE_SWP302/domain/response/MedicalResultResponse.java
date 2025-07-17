package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MedicalResultResponse {
    private Long resultId;
    private String doctorName;
    private String testName;
    private LocalDateTime examDate;
    private LocalDate resultDate;
    private String resultValue;
    private String conclusion;
}