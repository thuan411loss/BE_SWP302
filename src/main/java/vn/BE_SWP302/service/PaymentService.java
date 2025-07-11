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

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    public ApiResponse createPayment(PaymentRequest request) {
        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmount(invoice.getTotalAmount());
        payment.setMethod(request.getMethod());
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
}
