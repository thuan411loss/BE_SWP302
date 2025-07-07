package vn.BE_SWP302.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.Examination;
import vn.BE_SWP302.domain.request.ExaminationRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.ExaminationResponse;
import vn.BE_SWP302.repository.BookingRepository;
import vn.BE_SWP302.repository.ExaminationRepository;
import vn.BE_SWP302.repository.MedicalResultsRepository;

@Service
@RequiredArgsConstructor

public class ExaminationService {
	private final ExaminationRepository examinationRepository;
	private final BookingRepository bookingRepository;
	private final MedicalResultsRepository medicalResultsRepository;

	public ApiResponse createExamination(ExaminationRequest request) {
		Optional<Booking> booking = bookingRepository.findById(request.getBookingId());
		if (booking.isEmpty()) {
			return new ApiResponse(false, "Booking not found");
		}
		Examination exam = new Examination();
		exam.setBooking(booking.get());
		exam.setExamDate(LocalDateTime.now());
		exam.setDiagnosis(request.getDiagnosis());
		exam.setRecommendation(request.getRecommendation());
		examinationRepository.save(exam);
		return new ApiResponse(true, "Examination created successfully");
	}

	public List<ExaminationResponse> findAll() {
		return examinationRepository.findAll().stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	public Examination findById(Long id) {
		return examinationRepository.findById(id).orElse(null);
	}

	public ExaminationResponse findByIdResponse(Long id) {
		Examination examination = examinationRepository.findById(id).orElse(null);
		if (examination == null) {
			return null;
		}
		return toResponse(examination);
	}

	public List<ExaminationResponse> findByBookingId(Long bookingId) {
		return examinationRepository.findByBooking_BookingId(bookingId).stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	public List<ExaminationResponse> findByCustomerId(Long customerId) {
		return examinationRepository.findByCustomerId(customerId).stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	public Examination save(Examination examination) {
		return examinationRepository.save(examination);
	}

	public void delete(Long id) {
		examinationRepository.deleteById(id);
	}

	private ExaminationResponse toResponse(Examination e) {
		ExaminationResponse res = new ExaminationResponse();
		res.setExamId(e.getExamId());
		res.setName(e.getName());
		res.setExamDate(e.getExamDate());
		res.setDiagnosis(e.getDiagnosis());
		res.setRecommendation(e.getRecommendation());

		if (e.getBooking() != null) {
			res.setBookingId(e.getBooking().getBookingId());
			if (e.getBooking().getCustomer() != null)
				res.setCustomerName(e.getBooking().getCustomer().getName());
			if (e.getBooking().getWork() != null && e.getBooking().getWork().getDoctor() != null)
				res.setDoctorName(e.getBooking().getWork().getDoctor().getName());
			if (e.getBooking().getService() != null)
				res.setServiceName(e.getBooking().getService().getName());
		}

		// Lấy normalRange từ medical results
		medicalResultsRepository.findByExamination_ExamId(e.getExamId()).stream().findFirst()
				.ifPresent(mr -> res.setNormalRange(mr.getNormalRange()));

		return res;
	}
}
