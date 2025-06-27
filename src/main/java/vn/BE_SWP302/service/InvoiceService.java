package vn.BE_SWP302.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Invoice;
import vn.BE_SWP302.domain.request.InvoiceRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.repository.InvoiceRepository;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public ApiResponse createInvoice(InvoiceRequest request) {
        Invoice invoice = new Invoice();
        invoice.setUserId(request.getUserId());
        invoice.setAmount(request.getAmount());
        invoice.setCreatedDate(new Date());
        invoiceRepository.save(invoice);
        return new ApiResponse(true, "Invoice created successfully");
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    // Trong InvoiceService.java
    public List<Invoice> getInvoicesByUser(Long userId) {
        // Ví dụ: tìm theo customer id trong booking
        return invoiceRepository.findByBooking_Customer_Id(userId);
    }

}
