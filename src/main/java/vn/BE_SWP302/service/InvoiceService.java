package vn.BE_SWP302.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.Invoice;
import vn.BE_SWP302.domain.request.InvoiceRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.InvoiceResponse;
import vn.BE_SWP302.repository.BookingRepository;
import vn.BE_SWP302.repository.InvoiceRepository;
import vn.BE_SWP302.util.error.IdinvaliadException;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final BookingRepository bookingRepository;

    public ApiResponse createInvoice(InvoiceRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new IdinvaliadException("Booking not found"));

        Invoice invoice = new Invoice();
        invoice.setBooking(booking);
        invoice.setIssuedDate(LocalDateTime.now());
        // Lấy giá dịch vụ từ booking -> service
        invoice.setTotalAmount(booking.getService().getPrice());
        invoice.setStatus("PENDING");

        invoiceRepository.save(invoice);
        return new ApiResponse(true, "Invoice created successfully");
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    // Trong InvoiceService.java
    public List<InvoiceResponse> getInvoicesByUser(Long userId) {
        return invoiceRepository.findByBooking_Customer_Id(userId).stream().map(invoice -> {
            InvoiceResponse res = new InvoiceResponse();
            res.setInvoiceId(invoice.getInvoiceId());
            res.setIssuedDate(invoice.getIssuedDate());
            res.setTotalAmount(invoice.getTotalAmount());
            res.setStatus(invoice.getStatus());
            return res;
        }).collect(Collectors.toList());
    }

    // public InvoiceResponse getInvoiceByBookingId(Long bookingId) {
    // List<Invoice> invoices =
    // invoiceRepository.findByBooking_BookingId(bookingId);
    // if (invoices.isEmpty()) {
    // throw new IdinvaliadException("Invoice not found for booking ID: " +
    // bookingId);
    // }

    // Invoice invoice = invoices.get(0); // Lấy invoice đầu tiên
    // InvoiceResponse res = new InvoiceResponse();
    // res.setInvoiceId(invoice.getInvoiceId());
    // res.setIssuedDate(invoice.getIssuedDate());
    // res.setTotalAmount(invoice.getTotalAmount());
    // res.setStatus(invoice.getStatus());
    // return res;
    // }

    public InvoiceResponse getInvoiceByBookingId(Long bookingId) {
        List<Invoice> invoices = invoiceRepository.findByBooking_BookingId(bookingId);
        if (invoices.isEmpty()) {
            return null; // Trả về null thay vì throw exception
        }

        Invoice invoice = invoices.get(0);
        InvoiceResponse res = new InvoiceResponse();
        res.setInvoiceId(invoice.getInvoiceId());
        res.setIssuedDate(invoice.getIssuedDate());
        res.setTotalAmount(invoice.getTotalAmount());
        res.setStatus(invoice.getStatus());
        return res;
    }
}
