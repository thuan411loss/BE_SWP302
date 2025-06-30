package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentResponse {
    private Long paymentId;
    private BigDecimal amount;
    private String method;
    private LocalDateTime paymentDate;
}