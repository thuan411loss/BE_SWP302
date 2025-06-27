package vn.BE_SWP302.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.BE_SWP302.domain.response.DashboardResponse;
import vn.BE_SWP302.repository.*;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final InvoiceRepository invoiceRepository;
    private final TreatmentServicesRepository treatmentServicesRepository;
    private final FeedbackRepository feedbackRepository;
    private final TreatmentProgressRepository treatmentProgressRepository;

    public DashboardResponse getDashboardStats() {
        DashboardResponse response = new DashboardResponse();

        response.setTotalUsers(userRepository.count());
        response.setTotalDoctors(userRepository.countByRoleName("DOCTOR"));
        response.setTotalBookings(bookingRepository.count());
        response.setTodayBookings(bookingRepository.countByBookingDateBetween(
                LocalDate.now().atStartOfDay(), LocalDate.now().plusDays(1).atStartOfDay()));

        response.setTodayRevenue(invoiceRepository.sumTotalAmountByDate(LocalDate.now()));
        response.setMonthRevenue(
                invoiceRepository.sumTotalAmountByMonth(LocalDate.now().getMonthValue(), LocalDate.now().getYear()));

        response.setTotalServices(treatmentServicesRepository.count());
        response.setTotalFeedback(feedbackRepository.count());
        response.setOngoingTreatmentCount(treatmentProgressRepository.countOngoing());

        return response;
    }
}