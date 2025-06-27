package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicalResultsRequest {
    private Long examId;
    private String testName;
    private String resultValue;
    private String resultDate;
    private Long staffId;

}
