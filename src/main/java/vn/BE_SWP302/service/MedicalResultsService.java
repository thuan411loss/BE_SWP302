package vn.BE_SWP302.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.MedicalResults;
import vn.BE_SWP302.repository.MedicalResultsRepository;

@Service
public class MedicalResultsService {

	@Autowired
	MedicalResultsRepository medicalResultsRepository;

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
