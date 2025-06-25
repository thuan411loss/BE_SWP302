package vn.BE_SWP302.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Examination;
import vn.BE_SWP302.domain.MedicalResults;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.dto.ApiResponse;
import vn.BE_SWP302.domain.dto.MedicalResultsRequest;
import vn.BE_SWP302.repository.ExaminationRepository;
import vn.BE_SWP302.repository.MedicalResultsRepository;
import vn.BE_SWP302.repository.UserRepository;

@Service
@RequiredArgsConstructor

public class MedicalResultsService {

	private final MedicalResultsRepository medicalResultsRepository;
	private final ExaminationRepository examinationRepository;
	private final UserRepository userRepository;

	public ApiResponse createMedicalResults(MedicalResultsRequest request) {
		Optional<Examination> examination = examinationRepository.findById(request.getExamId());
		if (examination.isEmpty()) {
			return new ApiResponse(false, "Examination not found");
		}
		Optional<User> staff = userRepository.findById(request.getStaffId());
		if (staff.isEmpty()) {
			return new ApiResponse(false, "Staff not found");
		}
		MedicalResults results = new MedicalResults();
		results.setExamination(examination.get());
		results.setTestName(request.getTestName());
		results.setResultValue(request.getResultValue());
		results.setResultDate(LocalDate.parse(request.getResultDate()));
		results.setStaff(staff.get());
		medicalResultsRepository.save(results);
		return new ApiResponse(true, "Medical results created successfully");
	}

	public List<MedicalResults> findAll() {
		return medicalResultsRepository.findAll();
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
}
