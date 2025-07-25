package vn.BE_SWP302.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.request.PaymentRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.PaymentResponse;
import vn.BE_SWP302.domain.response.PaymentQRResponse;
import vn.BE_SWP302.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<List<PaymentResponse>> getByInvoice(@PathVariable Long invoiceId) {
        return ResponseEntity.ok(paymentService.getPaymentsByInvoice(invoiceId));
    }

    @GetMapping("/qr/{invoiceId}")
    public ResponseEntity<PaymentQRResponse> getPaymentQR(
            @PathVariable("invoiceId") Long invoiceId,
            @RequestParam(name = "comment", defaultValue = "") String comment) {
        return ResponseEntity.ok(paymentService.generatePaymentQR(invoiceId, comment));
    }
}