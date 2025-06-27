package vn.BE_SWP302.domain.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreatmentProgressResponse {
    private Long progressId;
    private Long scheduleId;
    private LocalDateTime progressDate;
    private String description;
    private String status;
}