package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TreatmentServiceResponse {
    private Long id;
    private String category;
    private String title;
    private String description;
    private String duration;
    private String successRate;
    private List<String> features;
    private String priceRange;
    private String currency;
    private String badge;

}
