package vn.BE_SWP302.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.TreatmentServices;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.request.TreatmentServicesRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.TreatmentServicesResponse;
import vn.BE_SWP302.repository.TreatmentServicesRepository;
import vn.BE_SWP302.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TreatmentServicesService {

	private final TreatmentServicesRepository repository;
	private final UserRepository userRepository;

	public ApiResponse create(TreatmentServicesRequest request) {
		Optional<User> user = userRepository.findById(request.getUserId());
		if (user.isEmpty())
			return new ApiResponse(false, "User not found");

		TreatmentServices service = new TreatmentServices();
		service.setUser(user.get());
		service.setName(request.getName());
		service.setDescription(request.getDescription());
		service.setType(request.getType());
		service.setFee(request.getFee());
		service.setDurationMinutes(request.getDurationMinutes());

		repository.save(service);
		return new ApiResponse(true, "Service created successfully");
	}

	public List<TreatmentServicesResponse> findAll() {
		return repository.findAll().stream().map(s -> {
			TreatmentServicesResponse res = new TreatmentServicesResponse();
			res.setServiceId(s.getServiceId());
			res.setName(s.getName());
			res.setDescription(s.getDescription());
			res.setType(s.getType());
			res.setFee(s.getFee());
			res.setDurationMinutes(s.getDurationMinutes());
			return res;
		}).collect(Collectors.toList());
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

	public TreatmentServices findByName(String name) {
		return repository.findByName(name);
	}
}
