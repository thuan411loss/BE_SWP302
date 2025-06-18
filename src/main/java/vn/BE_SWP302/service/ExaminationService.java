package vn.BE_SWP302.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.Examination;
import vn.BE_SWP302.repository.ExaminationRepository;

@Service
public class ExaminationService {

	@Autowired
	ExaminationRepository examinationRepository;

	public List<Examination> findAll() {
		return examinationRepository.findAll();
	}

	public Examination findById(Long id) {
		return examinationRepository.findById(id).orElse(null);
	}

	public Examination save(Examination examination) {
		return examinationRepository.save(examination);
	}

	public void delete(Long id) {
		examinationRepository.deleteById(id);
	}
}
