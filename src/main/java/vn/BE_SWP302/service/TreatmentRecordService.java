package vn.BE_SWP302.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.TreatmentRecord;
import vn.BE_SWP302.domain.request.TreatmentRecordRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.TreatmentRecordResponse;
import vn.BE_SWP302.repository.BookingRepository;
import vn.BE_SWP302.repository.TreatmentRecordRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreatmentRecordService {

    private final TreatmentRecordRepository treatmentRecordRepository;
    private final BookingRepository bookingRepository;

    public ApiResponse createTreatmentRecord(TreatmentRecordRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        TreatmentRecord record = new TreatmentRecord();
        record.setBooking(booking);
        record.setDiagnosis(request.getDiagnosis());
        record.setTreatmentPlan(request.getTreatmentPlan());
        record.setCreatedDate(LocalDateTime.now());

        treatmentRecordRepository.save(record);

        return new ApiResponse(true, "Treatment record created successfully");
    }

    public List<TreatmentRecordResponse> getRecordsByBooking(Long bookingId) {
        return treatmentRecordRepository.findByBooking_BookingId(bookingId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private TreatmentRecordResponse toResponse(TreatmentRecord record) {
        TreatmentRecordResponse response = new TreatmentRecordResponse();
        response.setId(record.getId());
        response.setDiagnosis(record.getDiagnosis());
        response.setTreatmentPlan(record.getTreatmentPlan());
        response.setCreatedDate(record.getCreatedDate());
        return response;
    }
}