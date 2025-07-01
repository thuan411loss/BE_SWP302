package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MedicalResultResponse {
    private Long resultId;
    private String testName;
    private String resultValue;
    private LocalDate resultDate;

}