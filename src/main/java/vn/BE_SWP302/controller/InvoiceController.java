package vn.BE_SWP302.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.request.InvoiceRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.InvoiceResponse;
import vn.BE_SWP302.service.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody InvoiceRequest request) {
        return ResponseEntity.ok(invoiceService.createInvoice(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvoiceResponse>> getByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(invoiceService.getInvoicesByUser(userId));
    }

    // @GetMapping("/booking/{bookingId}")
    // public ResponseEntity<InvoiceResponse>
    // getByBookingId(@PathVariable("bookingId") Long bookingId) {
    // return ResponseEntity.ok(invoiceService.getInvoiceByBookingId(bookingId));
    // }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<InvoiceResponse> getByBookingId(@PathVariable("bookingId") Long bookingId) {
        InvoiceResponse response = invoiceService.getInvoiceByBookingId(bookingId);
        if (response == null) {
            return ResponseEntity.notFound().build(); // Trả về 404
        }
        return ResponseEntity.ok(response);
    }
}
