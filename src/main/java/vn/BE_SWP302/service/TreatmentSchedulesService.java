package vn.BE_SWP302.service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.MedicalResults;
import vn.BE_SWP302.domain.TreatmentSchedules;
import vn.BE_SWP302.domain.dto.TreatmentScheduleRequest;
import vn.BE_SWP302.domain.dto.ApiResponse;
import vn.BE_SWP302.repository.MedicalResultsRepository;
import vn.BE_SWP302.repository.TreatmentSchedulesRepository;

@Service
@RequiredArgsConstructor
public class TreatmentSchedulesService {

	private final TreatmentSchedulesRepository treatmentSchedulesRepository;
	private final MedicalResultsRepository medicalResultsRepository;

	public ApiResponse createSchedule(TreatmentScheduleRequest request) {
		Optional<MedicalResults> medicalResults = medicalResultsRepository.findById(request.getResultId());
		if (medicalResults.isEmpty()) {
			throw new IllegalArgumentException("Medical results not found");
		}
		TreatmentSchedules schedule = new TreatmentSchedules();
		schedule.setMedicalResult(medicalResults.get());
		schedule.setStartDate(LocalDate.parse(request.getStartDate()));
		schedule.setEndDate(LocalDate.parse(request.getEndDate()));
		schedule.setStatus("Scheduled");
		schedule.setNotes(request.getNotes());
		treatmentSchedulesRepository.save(schedule);
		return new ApiResponse(true, "Schedule Created Successfully");
	}

	public List<TreatmentSchedules> viewAllSchedules() {
		return treatmentSchedulesRepository.findAll();
	}

	public TreatmentSchedules viewScheduleById(Long id) {
		return treatmentSchedulesRepository.findById(id).orElse(null);
	}

	public TreatmentSchedules updateSchedule(Long id, TreatmentSchedules schedule) {
		TreatmentSchedules existing = treatmentSchedulesRepository.findById(id).orElse(null);
		if (existing != null) {
			existing.setStartDate(schedule.getStartDate());
			existing.setEndDate(schedule.getEndDate());
			existing.setStatus(schedule.getStatus());
			existing.setNotes(schedule.getNotes());
			return treatmentSchedulesRepository.save(existing);
		}
		return null;
	}

	// public void deleteSchedule(Long id) {
	// treatmentSchedulesRepository.deleteAllById(id);
	// }

	public TreatmentSchedules save(TreatmentSchedules schedule) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	public List<TreatmentSchedules> getSchedulesByResult(Long resultId) {
		return treatmentSchedulesRepository.findByMedicalResult_ResultId(resultId);
	}
}