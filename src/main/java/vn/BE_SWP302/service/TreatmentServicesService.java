package vn.BE_SWP302.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.TreatmentServices;
import vn.BE_SWP302.repository.TreatmentServicesRepository;

@Service
public class TreatmentServicesService {

	@Autowired
	TreatmentServicesRepository repository;

	public List<TreatmentServices> findAll() {
		return repository.findAll();
	}

	public TreatmentServices findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public TreatmentServices save(TreatmentServices service) {
		return repository.save(service);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
