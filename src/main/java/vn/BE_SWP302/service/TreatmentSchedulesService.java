package vn.BE_SWP302.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.TreatmentSchedules;
import vn.BE_SWP302.repository.TreatmentSchedulesRepository;

@Service
@RequiredArgsConstructor
public class TreatmentSchedulesService {

	private final TreatmentSchedulesRepository treatmentSchedulesRepository = null;
	private final MedicalResultsRepository medicalResultsRepository = null;

	public TreatmentSchedules createSchedule(TreatmentSchedulesRequest request) {
		if (!medicalResultsRepository.existsById(request.getMedicalResultsId())) {
			throw new IllegalArgumentException("Medical results not found");
		TreatmentSchedules schedule = new TreatmentSchedules();
		schedule.setMedicalResultsId(request.getMedicalResultsId());
		schedule.setStartDate(request.getStartDate());
		schedule.setEndDate(request.getEndDate());
		schedule.setStatus("Scheduled");
		schedule.setNotes(request.getNotes());
		treatmentSchedulesRepository.save(schedule);
		return new ApiResponse(true, "Schedule Created Successfully");
		}

		return treatmentSchedulesRepository.save(schedule);
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

	public List<TreatmentSchedules> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public TreatmentSchedules findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public TreatmentSchedules save(TreatmentSchedules schedule) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(int id) {
		// TODO Auto-generated method stub

	}
}