package vn.BE_SWP302.domain.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedbackResponse {
    private Long feedbackId;
    private Long userId;
    private Long doctorId;
    private int rating;
    private String comment;
    private LocalDateTime createdDate;
}
