package vn.BE_SWP302.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.BE_SWP302.domain.TreatmentProgress;
import vn.BE_SWP302.domain.TreatmentSchedule;
import vn.BE_SWP302.domain.request.TreatmentProgressRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.TreatmentProgressResponse;
import vn.BE_SWP302.repository.TreatmentProgressRepository;
import vn.BE_SWP302.repository.TreatmentSchedulesRepository;

@Service
@RequiredArgsConstructor
public class TreatmentProgressService {

	private final TreatmentProgressRepository treatmentProgressRepository;
	private final TreatmentSchedulesRepository treatmentScheduleRepository;

	public ApiResponse create(TreatmentProgressRequest request) {
		Optional<TreatmentSchedule> scheduleOpt = treatmentScheduleRepository.findById(request.getScheduleId());
		if (scheduleOpt.isEmpty()) {
			return new ApiResponse(false, "Treatment Schedule not found");
		}
		TreatmentProgress progress = new TreatmentProgress();
		progress.setTreatmentSchedule(scheduleOpt.get());
		progress.setProgressDate(request.getProgressDate());
		progress.setDescription(request.getDescription());
		progress.setStatus(request.getStatus());

		treatmentProgressRepository.save(progress);
		return new ApiResponse(true, "Treatment Progress created successfully");
	}

	public List<TreatmentProgressResponse> getAll() {
		return treatmentProgressRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	public TreatmentProgressResponse getById(Long id) {
		TreatmentProgress progress = treatmentProgressRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Progress not found"));
		return mapToResponse(progress);
	}

	public List<TreatmentProgressResponse> getBySchedule(Long scheduleId) {
		return treatmentProgressRepository.findByTreatmentSchedule_TreatmentScheduleId(scheduleId).stream()
				.map(this::mapToResponse).collect(Collectors.toList());
	}

	public ApiResponse update(Long id, TreatmentProgressRequest request) {
		TreatmentProgress progress = treatmentProgressRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Progress not found"));

		Optional<TreatmentSchedule> scheduleOpt = treatmentScheduleRepository.findById(request.getScheduleId());
		if (scheduleOpt.isEmpty()) {
			return new ApiResponse(false, "Treatment Schedule not found");
		}

		progress.setTreatmentSchedule(scheduleOpt.get());
		progress.setProgressDate(request.getProgressDate());
		progress.setDescription(request.getDescription());
		progress.setStatus(request.getStatus());

		treatmentProgressRepository.save(progress);

		return new ApiResponse(true, "Treatment progress updated successfully");
	}

	public ApiResponse delete(Long id) {
		if (!treatmentProgressRepository.existsById(id)) {
			return new ApiResponse(false, "Progress not found");
		}
		treatmentProgressRepository.deleteById(id);
		return new ApiResponse(true, "Progress deleted successfully");
	}

	public List<TreatmentProgressResponse> getByCustomerId(Long customerId) {
		return treatmentProgressRepository.findByCustomerId(customerId)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	private TreatmentProgressResponse mapToResponse(TreatmentProgress progress) {
		TreatmentProgressResponse res = new TreatmentProgressResponse();
		res.setProgressId(progress.getProgressId());
		res.setScheduleId(progress.getTreatmentSchedule().getTreatmentScheduleId());
		res.setProgressDate(progress.getProgressDate());
		res.setDescription(progress.getDescription());
		res.setStatus(progress.getStatus());
		return res;
	}
}