package vn.BE_SWP302.service;

<<<<<<< HEAD
import java.time.LocalDateTime;
=======
import java.util.Date;
>>>>>>> 52e7f7a56656db377c4bc8fb9da1e94e00aa7b80
import java.util.List;
import java.util.Optional;

import org.hibernate.jpa.internal.ExceptionMapperLegacyJpaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.micrometer.core.ipc.http.HttpSender.Request;
import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.Examination;
import vn.BE_SWP302.domain.dto.ApiResponse;
import vn.BE_SWP302.repository.BookingRepository;
import vn.BE_SWP302.repository.ExaminationRepository;
import vn.BE_SWP302.domain.dto.ExaminationRequest;

@Service
public class ExaminationService {
	private final ExaminationRepository examinationRepository;
	private final BookingRepository bookingRepository;

	@Autowired
	public ExaminationService(ExaminationRepository examinationRepository, BookingRepository bookingRepository) {
		this.examinationRepository = examinationRepository;
		this.bookingRepository = bookingRepository;
	}

	public ApiResponse createExamination(ExaminationRequest request) {
		Optional<Booking> booking = bookingRepository.findById(request.getBookingId());
		if (booking.isEmpty()) {
			return new ApiResponse(false, "Booking not found");
		}
		Examination exam = new Examination();
		exam.setBooking(booking.get());
		exam.setExam_date(LocalDateTime.now());
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
