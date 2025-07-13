package vn.BE_SWP302.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Invoice;
import vn.BE_SWP302.domain.Payment;
import vn.BE_SWP302.domain.request.PaymentRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.PaymentResponse;
import vn.BE_SWP302.repository.InvoiceRepository;
import vn.BE_SWP302.repository.PaymentRepository;
import vn.BE_SWP302.util.error.IdinvaliadException;
import vn.BE_SWP302.service.QRCodeService;
import vn.BE_SWP302.domain.response.PaymentQRResponse;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final QRCodeService qrCodeService;

    public ApiResponse createPayment(PaymentRequest request) {
        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmount(invoice.getTotalAmount());
        payment.setMethod("BANKING");
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);

        // Cập nhật trạng thái hóa đơn
        invoice.setStatus("PAID");
        invoiceRepository.save(invoice);

        return new ApiResponse(true, "Payment recorded successfully");
    }

    public List<PaymentResponse> getPaymentsByInvoice(Long invoiceId) {
        return paymentRepository.findByInvoice_InvoiceId(invoiceId).stream().map(payment -> {
            PaymentResponse response = new PaymentResponse();
            response.setPaymentId(payment.getPaymentId());
            response.setAmount(payment.getAmount());
            response.setMethod(payment.getMethod());
            response.setPaymentDate(payment.getPaymentDate());
            return response;
        }).collect(Collectors.toList());
    }

    public PaymentQRResponse generatePaymentQR(Long invoiceId, String comment) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IdinvaliadException("Invoice not found"));

        PaymentQRResponse response = new PaymentQRResponse();
        response.setInvoiceId(invoice.getInvoiceId());
        response.setAmount(invoice.getTotalAmount());
        response.setBankInfo("ABC Bank");
        response.setAccountNumber("123456789");

        // Tạo nội dung QR
        String serviceName = invoice.getBooking().getService().getName();
        String description = "Thanh toan dich vu " + serviceName;
        String qrContent = qrCodeService.generatePaymentQRContent(invoice.getTotalAmount(), description, comment);
        response.setQrContent(qrContent);

        try {
            // Tạo QR code base64
            String qrCodeBase64 = qrCodeService.generateQRCodeBase64(qrContent);
            response.setQrCodeBase64(qrCodeBase64);
        } catch (Exception e) {
            // Nếu có lỗi tạo QR, vẫn trả về thông tin khác
            response.setQrCodeBase64(null);
        }

        return response;
    }
}
