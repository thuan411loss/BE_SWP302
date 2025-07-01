package vn.BE_SWP302.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.TreatmentServices;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.request.TreatmentServiceRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.TreatmentServiceResponse;
import vn.BE_SWP302.repository.TreatmentServicesRepository;
import vn.BE_SWP302.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TreatmentServicesService {

	private final TreatmentServicesRepository repository;


	public TreatmentServiceResponse create(TreatmentServiceRequest dto) {
		TreatmentServices service = new TreatmentServices();
		service.setCategory(dto.getCategory());
		service.setTitle(dto.getTitle());
		service.setDescription(dto.getDescription());
		service.setDuration(dto.getDuration());
		service.setSuccessRate(dto.getSuccessRate());
		service.setFeatures(dto.getFeatures());
		service.setPriceRange(dto.getPriceRange());
		service.setCurrency(dto.getCurrency());
		service.setBadge(dto.getBadge());

		TreatmentServices saved = repository.save(service);
		return mapToResponse(saved);
	}

	public List<TreatmentServiceResponse> getAll() {
		return repository.findAll()
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	public TreatmentServiceResponse getById(Long id) {
		TreatmentServices service = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Service not found"));
		return mapToResponse(service);
	}

	public TreatmentServiceResponse update(Long id, TreatmentServiceRequest dto) {
		TreatmentServices service = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Service not found"));

		service.setCategory(dto.getCategory());
		service.setTitle(dto.getTitle());
		service.setDescription(dto.getDescription());
		service.setDuration(dto.getDuration());
		service.setSuccessRate(dto.getSuccessRate());
		service.setFeatures(dto.getFeatures());
		service.setPriceRange(dto.getPriceRange());
		service.setCurrency(dto.getCurrency());
		service.setBadge(dto.getBadge());

		TreatmentServices updated = repository.save(service);
		return mapToResponse(updated);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public TreatmentServices findByName(String name) {
		return repository.findByName(name);
	}

	private TreatmentServiceResponse mapToResponse(TreatmentServices service) {
		TreatmentServiceResponse response = new TreatmentServiceResponse();
		response.setId(service.getService_id());
		response.setCategory(service.getCategory());
		response.setTitle(service.getTitle());
		response.setDescription(service.getDescription());
		response.setDuration(service.getDuration());
		response.setSuccessRate(service.getSuccessRate());
		response.setFeatures(service.getFeatures());
		response.setPriceRange(service.getPriceRange());
		response.setCurrency(service.getCurrency());
		response.setBadge(service.getBadge());

		return response;
	}
}
