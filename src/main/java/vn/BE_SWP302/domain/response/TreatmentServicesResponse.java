package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TreatmentServicesResponse {
    private Long serviceId;
    private String name;
    private String description;
    private String type;
    private Double fee;
    private Integer durationMinutes;
}
