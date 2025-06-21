package vn.BE_SWP302.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.MedicalResults;
import vn.BE_SWP302.repository.MedicalResultsRepository;

@Service
public class MedicalResultsService {
	private final MedicalResultsRepository medicalResultsRepository;
	private final ExaminationRepository examinationRepository;

	@Autowired
	MedicalResultsRepository medicalResultsRepository;

	public ApiResponse createMedicalResults(MedicalResultsRequest request) {
		Optional<Examination> examination = examinationRepository.findById(request.getExaminationId());
		if (examination.isEmpty()) {
			return new ApiResponse(false, "Examination not found");
		}
		MedicalResults results = new MedicalResults();
		results.setExaminationId(request.getExaminationId());
		results.setTestName(request.getTestName());
		results.setResultValue(request.getResultValue());
		results.setResultDate(request.getResultDate());
		results.setStaff(request.getStaff());
		// results.setStaffId(request.getStaffId()); dong nay goi staff
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
}
