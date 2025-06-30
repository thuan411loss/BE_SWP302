package vn.BE_SWP302.domain.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequest {
  private Long invoiceId;
  private BigDecimal amount;
  private String method;
}
