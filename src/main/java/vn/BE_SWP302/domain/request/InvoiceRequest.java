package vn.BE_SWP302.domain.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceRequest {
    private Long bookingId;
    private BigDecimal totalAmount;
    private String status;
}
