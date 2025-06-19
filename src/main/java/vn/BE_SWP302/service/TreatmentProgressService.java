package vn.BE_SWP302.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.TreatmentProgress;
import vn.BE_SWP302.repository.TreatmentProgressRepository;

@Service
public class TreatmentProgressService {

	@Autowired
	TreatmentProgressRepository repository;

	public List<TreatmentProgress> findAll() {
		return repository.findAll();
	}

	public TreatmentProgress findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public TreatmentProgress save(TreatmentProgress progress) {
		return repository.save(progress);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
