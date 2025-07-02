package vn.BE_SWP302.domain.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreatmentProgressRequest {
    private Long scheduleId;
    private LocalDateTime progressDate;
    private String description;
    private String status;
}