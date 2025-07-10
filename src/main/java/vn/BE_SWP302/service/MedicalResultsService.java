package vn.BE_SWP302.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Examination;
import vn.BE_SWP302.domain.MedicalResults;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.request.MedicalResultsRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.MedicalResultResponse;
import vn.BE_SWP302.repository.ExaminationRepository;
import vn.BE_SWP302.repository.MedicalResultsRepository;
import vn.BE_SWP302.repository.UserRepository;
import vn.BE_SWP302.util.SecurityUtil;

@Service
@RequiredArgsConstructor

public class MedicalResultsService {

	private final MedicalResultsRepository medicalResultsRepository;
	private final ExaminationRepository examinationRepository;
	private final UserRepository userRepository;

	public ApiResponse createMedicalResults(MedicalResultsRequest request) {
		Optional<Examination> examOpt = examinationRepository.findById(request.getExamId());
		if (examOpt.isEmpty()) {
			return new ApiResponse(false, "Examination not found");
		}
		Examination exam = examOpt.get();

		MedicalResults result = new MedicalResults();
		result.setExamination(exam);

		String username = SecurityUtil.getCurrentUserLogin().orElse(null);
		User doctor = userRepository.findByEmail(username);
		result.setDoctor(doctor);
		// Lấy testName, examDate từ Examination
		result.setTestName(exam.getName());
		result.setExamDate(exam.getExamDate());

		result.setResultValue(request.getResultValue());
		result.setResultDate(request.getResultDate());
		result.setConclusion(request.getConclusion());

		medicalResultsRepository.save(result);
		return new ApiResponse(true, "Medical result created successfully");
	}

	public List<MedicalResultResponse> getByExam(Long examId) {
		return medicalResultsRepository.findByExamination_ExamId(examId)
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
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
		dto.setExamDate(r.getExamDate());
		dto.setResultValue(r.getResultValue());
		dto.setResultDate(r.getResultDate());
		dto.setConclusion(r.getConclusion());
		dto.setDoctorName(r.getDoctor() != null ? r.getDoctor().getName() : null);
		return dto;
	}
}
