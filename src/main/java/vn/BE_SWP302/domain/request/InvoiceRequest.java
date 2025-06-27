package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceRequest {
    private Long userId;
    private Double amount;
    private String description;

}
