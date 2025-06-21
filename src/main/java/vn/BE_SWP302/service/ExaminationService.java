package vn.BE_SWP302.service;

import java.util.Date;
import java.util.List;

import org.hibernate.jpa.internal.ExceptionMapperLegacyJpaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.micrometer.core.ipc.http.HttpSender.Request;
import vn.BE_SWP302.domain.Examination;
import vn.BE_SWP302.repository.ExaminationRepository;

@Service
public class ExaminationService {
	private final ExaminationRepository examinationRepository;
	private final BookingRepository bookingRepository;

	@Autowired
	ExaminationRepository examinationRepository;

	public ApiResponse createExamination(ExaminationRequest request) {
		Optional<Booking> booking = bookingRepository.findById(request.getBookingId());
		if (booking.isEmpty()) {
			return new ApiResponse(false, "Booking not found");
		}
		Examination exam = new Examination();
		exam.setBookingId(Request.getBookingId());
		exam.setExam_date(new Date());
		exam.setDiagnosis(request.getDiagnosis());
		exam.setRecommendation(request.getRecommendation());
		examinationRepository.save(exam);
		return new ApiResponse(true, "Examination created successfully");
	}

	public List<Examination> findAll() {
		return examinationRepository.findAll();
	}

	public Examination findById(Long id) {
		return examinationRepository.findById(id).orElse(null);
	}

	public Examination save(Examination examination) {
		return examinationRepository.save(examination);
	}

	public void delete(Long id) {
		examinationRepository.deleteById(id);
	}
}
