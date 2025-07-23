package vn.BE_SWP302.domain.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceResponse {
    private Long invoiceId;
    private LocalDateTime issuedDate;
    private Double totalAmount;
    private String status;
}