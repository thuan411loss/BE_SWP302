package vn.BE_SWP302.service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.MedicalResults;
import vn.BE_SWP302.domain.TreatmentSchedules;
import vn.BE_SWP302.domain.request.TreatmentScheduleRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.TreatmentScheduleResponse;
import vn.BE_SWP302.repository.MedicalResultsRepository;
import vn.BE_SWP302.repository.TreatmentSchedulesRepository;

@Service
@RequiredArgsConstructor
public class TreatmentSchedulesService {

	private final TreatmentSchedulesRepository treatmentScheduleRepository;
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
		treatmentScheduleRepository.save(schedule);
		return new ApiResponse(true, "Schedule Created Successfully");
	}

	public List<TreatmentSchedules> viewAllSchedules() {
		return treatmentScheduleRepository.findAll();
	}

	public TreatmentSchedules viewScheduleById(Long id) {
		return treatmentScheduleRepository.findById(id).orElse(null);
	}

	public List<TreatmentScheduleResponse> getAll() {
		return treatmentScheduleRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	public TreatmentScheduleResponse getById(Long id) {
		TreatmentSchedules schedule = treatmentScheduleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Schedule not found"));
		return mapToResponse(schedule);
	}

	public ApiResponse update(Long id, TreatmentScheduleRequest request) {
		TreatmentSchedules schedule = treatmentScheduleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Schedule not found"));

		Optional<MedicalResults> resultOpt = medicalResultsRepository.findById(request.getResultId());
		if (resultOpt.isEmpty()) {
			return new ApiResponse(false, "Medical result not found");
		}

		schedule.setMedicalResult(resultOpt.get());
		schedule.setStartDate(LocalDate.parse(request.getStartDate()));
		schedule.setEndDate(LocalDate.parse(request.getEndDate()));
		schedule.setStatus(request.getStatus());
		schedule.setNotes(request.getNotes());

		treatmentScheduleRepository.save(schedule);

		return new ApiResponse(true, "Treatment schedule updated successfully");
	}

	public TreatmentSchedules save(TreatmentSchedules schedule) {
		// TODO Auto-generated method stub
		return null;
	}

	public ApiResponse delete(Long id) {
		if (!treatmentScheduleRepository.existsById(id)) {
			return new ApiResponse(false, "Schedule not found");
		}
		treatmentScheduleRepository.deleteById(id);
		return new ApiResponse(true, "Schedule deleted successfully");
	}

	public List<TreatmentSchedules> getSchedulesByResult(Long resultId) {
		return treatmentScheduleRepository.findByMedicalResult_ResultId(resultId);
	}

	private TreatmentScheduleResponse mapToResponse(TreatmentSchedules schedule) {
		TreatmentScheduleResponse res = new TreatmentScheduleResponse();
		res.setScheduleId(schedule.getScheduleId());
		res.setMedicalResultId(schedule.getMedicalResult().getResultId());
		res.setStartDate(schedule.getStartDate());
		res.setEndDate(schedule.getEndDate());
		res.setStatus(schedule.getStatus());
		res.setNotes(schedule.getNotes());
		return res;
	}
}