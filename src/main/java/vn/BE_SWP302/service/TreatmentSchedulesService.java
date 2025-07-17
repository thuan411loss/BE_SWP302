package vn.BE_SWP302.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Examination;
import vn.BE_SWP302.domain.MedicalResults;
import vn.BE_SWP302.domain.TreatmentSchedule;
import vn.BE_SWP302.domain.request.TreatmentScheduleRequest;
import vn.BE_SWP302.domain.response.TreatmentScheduleResponse;
import vn.BE_SWP302.repository.MedicalResultsRepository;
import vn.BE_SWP302.repository.TreatmentSchedulesRepository;
import vn.BE_SWP302.repository.ExaminationRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TreatmentSchedulesService {

	private final TreatmentSchedulesRepository treatmentSchedulesRepository;
	private final MedicalResultsRepository medicalResultsRepository;
	private final ExaminationRepository examinationRepository;

	public TreatmentScheduleResponse createSchedule(TreatmentScheduleRequest request) {
		// Lấy danh sách Examination theo bookingId
		List<Examination> exams = examinationRepository
				.findByBooking_BookingId(request.getBookingId());
		if (exams == null || exams.isEmpty()) {
			throw new RuntimeException("No examination found for this booking");
		}
		Examination exam = exams.get(exams.size() - 1); // lấy cái mới nhất

		// Lấy danh sách MedicalResults theo examId
		List<MedicalResults> results = medicalResultsRepository.findByExamination_ExamId(exam.getExamId());
		if (results == null || results.isEmpty()) {
			throw new RuntimeException("No medical result found for this booking");
		}
		MedicalResults result = results.get(results.size() - 1); // lấy cái mới nhất

		TreatmentSchedule schedule = new TreatmentSchedule();
		schedule.setStageName(request.getStageName());
		schedule.setStartDate(request.getStartDate());
		schedule.setEndDate(request.getEndDate());
		schedule.setStatus(request.getStatus());
		schedule.setNotes(String.join("\n", request.getActivities()));
		schedule.setMedicalResult(result);

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