package vn.BE_SWP302.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

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

    
}
