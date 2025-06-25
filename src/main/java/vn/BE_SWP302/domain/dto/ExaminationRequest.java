package vn.BE_SWP302.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExaminationRequest {
    private Long bookingId;
    private String diagnosis;
    private String recommendation;
}
