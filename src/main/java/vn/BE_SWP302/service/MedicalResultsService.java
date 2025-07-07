package vn.BE_SWP302.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Examination;
import vn.BE_SWP302.domain.MedicalResults;
import vn.BE_SWP302.domain.request.MedicalResultsRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.MedicalResultResponse;
import vn.BE_SWP302.repository.ExaminationRepository;
import vn.BE_SWP302.repository.MedicalResultsRepository;

@Service
@RequiredArgsConstructor

public class MedicalResultsService {

	private final MedicalResultsRepository medicalResultsRepository;
	private final ExaminationRepository examinationRepository;

	public ApiResponse createMedicalResults(MedicalResultsRequest request) {
		Optional<Examination> examination = examinationRepository.findById(request.getExamId());
		if (examination.isEmpty()) {
			return new ApiResponse(false, "Examination not found");
		}

		MedicalResults results = new MedicalResults();
		results.setExamination(examination.get());
		results.setTestName(request.getTestName());
		results.setResultValue(request.getResultValue());
		results.setResultDate(request.getResultDate());
		medicalResultsRepository.save(results);
		return new ApiResponse(true, "Medical results created successfully");
	}

	public List<MedicalResultResponse> getByExam(Long examId) {
		return medicalResultsRepository.findByExamination_ExamId(examId)
				.stream()
				.map(r -> {
					MedicalResultResponse dto = new MedicalResultResponse();
					dto.setResultId(r.getResultId());
					dto.setTestName(r.getTestName());
					dto.setResultValue(r.getResultValue());
					dto.setResultDate(r.getResultDate());
					// dto.setStaffName(r.getStaff().getFullName()); liên quan đến bảng user
					return dto;
				}).collect(Collectors.toList());
	}

	public MedicalResults findById(Long id) {
		return medicalResultsRepository.findById(id).orElse(null);
	}

	public MedicalResults save(MedicalResults result) {
		return medicalResultsRepository.save(result);
	}

	public void delete(Long id) {
		medicalResultsRepository.deleteById(id);
	}

	public List<MedicalResults> getResultsByExamId(Long examId) {
		return medicalResultsRepository.findByExamination_ExamId(examId);
	}

	public List<MedicalResultResponse> getByCustomerId(Long customerId) {
		// Truy vấn custom ở repository hoặc lọc qua Examination → Booking → Customer
		// Giả sử bạn đã có method findByCustomerId ở repository:
		return medicalResultsRepository.findByCustomerId(customerId)
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	public MedicalResultResponse getById(Long resultId) {
		MedicalResults r = medicalResultsRepository.findById(resultId)
				.orElseThrow(() -> new RuntimeException("Medical result not found"));
		return toResponse(r);
	}

	private MedicalResultResponse toResponse(MedicalResults r) {
		MedicalResultResponse dto = new MedicalResultResponse();
		dto.setResultId(r.getResultId());
		dto.setTestName(r.getTestName());
		dto.setResultValue(r.getResultValue());
		dto.setResultDate(r.getResultDate());
		dto.setConclusion(r.getConclusion());
		// Lấy tên bác sĩ từ Examination → Booking → WorkSchedule → Doctor
		if (r.getExamination() != null && r.getExamination().getBooking() != null
				&& r.getExamination().getBooking().getWork() != null
				&& r.getExamination().getBooking().getWork().getDoctor() != null) {
			dto.setDoctorName(r.getExamination().getBooking().getWork().getDoctor().getName());
		}
		return dto;
	}
}
