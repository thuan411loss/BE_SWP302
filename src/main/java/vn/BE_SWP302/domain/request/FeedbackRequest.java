package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedbackRequest {
    private Long userId;
    private Long doctorId;
    private int rating;
    private String comment;

}
