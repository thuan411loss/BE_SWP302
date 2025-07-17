package vn.BE_SWP302.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.MedicalResults;
import vn.BE_SWP302.domain.TreatmentSchedule;
import vn.BE_SWP302.domain.request.TreatmentScheduleRequest;
import vn.BE_SWP302.domain.response.TreatmentScheduleResponse;
import vn.BE_SWP302.repository.MedicalResultsRepository;
import vn.BE_SWP302.repository.TreatmentSchedulesRepository;
import vn.BE_SWP302.repository.ExaminationRepository;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.repository.UserRepository;
import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.repository.BookingRepository;
import vn.BE_SWP302.domain.Examination;

@Service
@RequiredArgsConstructor
@Transactional
public class TreatmentSchedulesService {

	private final TreatmentSchedulesRepository treatmentSchedulesRepository;
	private final MedicalResultsRepository medicalResultsRepository;
	private final ExaminationRepository examinationRepository;
	private final UserRepository userRepository;
	private final BookingRepository bookingRepository;

	public TreatmentScheduleResponse createSchedule(TreatmentScheduleRequest request) {
		// Lấy user theo customerId
		User customer = userRepository.findById(request.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		// Lấy booking mới nhất của customer
		List<Booking> bookings = bookingRepository.findByCustomerOrderByBookingDateDesc(customer);
		if (bookings.isEmpty())
			throw new RuntimeException("No booking found for this customer");
		Booking latestBooking = bookings.get(0);

		// Lấy examination theo booking
		List<Examination> exams = examinationRepository.findByBooking_BookingId(latestBooking.getBookingId());
		if (exams.isEmpty())
			throw new RuntimeException("No examination found for this booking");
		Examination latestExam = exams.get(exams.size() - 1);

		// Lấy medical result theo exam
		List<MedicalResults> results = medicalResultsRepository.findByExamination_ExamId(latestExam.getExamId());
		if (results.isEmpty())
			throw new RuntimeException("No medical result found for this examination");
		MedicalResults latestResult = results.get(results.size() - 1);

		TreatmentSchedule schedule = new TreatmentSchedule();
		schedule.setStageName(request.getStageName());
		schedule.setStartDate(request.getStartDate());
		schedule.setEndDate(request.getEndDate());
		schedule.setStatus(request.getStatus());
		schedule.setNotes(String.join("\n", request.getActivities()));
		schedule.setMedicalResult(latestResult);

		schedule = treatmentSchedulesRepository.save(schedule);
		return toResponse(schedule);
	}

	public List<TreatmentScheduleResponse> getSchedulesByResultId(Long resultId) {
		return treatmentSchedulesRepository.findByMedicalResult_ResultId(resultId)
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	public TreatmentScheduleResponse updateSchedule(Long id, TreatmentScheduleRequest request) {
		TreatmentSchedule schedule = treatmentSchedulesRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Schedule not found"));

		schedule.setStageName(request.getStageName());
		schedule.setStartDate(request.getStartDate());
		schedule.setEndDate(request.getEndDate());
		schedule.setStatus(request.getStatus());
		schedule.setNotes(String.join("\n", request.getActivities()));

		return toResponse(treatmentSchedulesRepository.save(schedule));
	}

	public List<TreatmentScheduleResponse> getSchedulesByCustomerIdFromBooking(Long customerId) {
		return treatmentSchedulesRepository.findByMedicalResult_Examination_Booking_CustomerId(customerId)
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	public List<TreatmentScheduleResponse> getAllSchedules() {
		return treatmentSchedulesRepository.findAll()
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	public void deleteSchedule(Long id) {
		treatmentSchedulesRepository.deleteById(id);
	}

	private TreatmentScheduleResponse toResponse(TreatmentSchedule s) {
		TreatmentScheduleResponse res = new TreatmentScheduleResponse();
		res.setScheduleId(s.getTreatmentScheduleId());
		res.setStageName(s.getStageName());
		res.setStartDate(s.getStartDate());
		res.setEndDate(s.getEndDate());
		res.setStatus(s.getStatus());
		res.setActivities(Arrays.asList(s.getNotes().split("\\n")));
		return res;
	}
}