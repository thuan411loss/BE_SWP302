package vn.BE_SWP302.domain.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TreatmentServicesRequest {
    private String name;
    private String category;
    private String title;
    private String description;
    private String duration;
    private String successRate;
    private List<String> features;
    private double price;
    private String currency;
    private String badge;
}