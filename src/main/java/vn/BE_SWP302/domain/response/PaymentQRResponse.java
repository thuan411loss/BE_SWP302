package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentQRResponse {
    private Long invoiceId;
    private Double amount;
    private String qrContent;
    private String qrCodeBase64;
    private String bankInfo;
    private String accountNumber;
}