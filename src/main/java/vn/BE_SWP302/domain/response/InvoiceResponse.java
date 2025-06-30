package vn.BE_SWP302.domain.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceResponse {
    private Long invoiceId;
    private LocalDateTime issuedDate;
    private BigDecimal totalAmount;
    private String status;
}