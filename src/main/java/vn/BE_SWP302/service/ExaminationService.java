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

@Service
@RequiredArgsConstructor

public class ExaminationService {
	private final ExaminationRepository examinationRepository;
	private final BookingRepository bookingRepository;

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
		return examinationRepository.findAll().stream().map(e -> {
			ExaminationResponse res = new ExaminationResponse();
			res.setExamId(e.getExamId());
			res.setExamDate(e.getExamDate());
			res.setDiagnosis(e.getDiagnosis());
			res.setRecommendation(e.getRecommendation());
			return res;
		}).collect(Collectors.toList());
	}

	public Examination findById(Long id) {
		return examinationRepository.findById(id).orElse(null);
	}

	public List<ExaminationResponse> findByBookingId(Long bookingId) {
		return examinationRepository.findByBooking_BookingId(bookingId).stream().map(e -> {
			ExaminationResponse res = new ExaminationResponse();
			res.setExamId(e.getExamId());
			res.setExamDate(e.getExamDate());
			res.setDiagnosis(e.getDiagnosis());
			res.setRecommendation(e.getRecommendation());
			return res;
		}).collect(Collectors.toList());
	}

	public Examination save(Examination examination) {
		return examinationRepository.save(examination);
	}

	public void delete(Long id) {
		examinationRepository.deleteById(id);
	}
}
