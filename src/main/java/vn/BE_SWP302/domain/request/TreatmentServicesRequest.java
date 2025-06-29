package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TreatmentServicesRequest {
    private Long userId;
    private String name;
    private String description;
    private String type;
    private Double fee;
    private Integer durationMinutes;
}