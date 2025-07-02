package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequest {
  private Long invoiceId;
  private Double amount;
  private String method;
}
