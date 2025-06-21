package vn.BE_SWP302.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final InvoiceRepository invoiceRepository;

    public DastboardReport generateReport() {
        long userCount = userRepository.count();
        long totalBookings = bookingRepository.count();
        double totalRevenue = invoiceRepository.findAll().stream().mapToDouble(Invoice::getAmount).sum();
        return new DashboardReport(userCount, totalBookings, totalRevenue);
    }

}
