package vn.BE_SWP302.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.WorkSchedule;
import vn.BE_SWP302.domain.dto.ApiResponse;
import vn.BE_SWP302.repository.WorkScheduleRepository;

@Service
public class WorkScheduleService {
	private final WorkScheduleRepository workScheduleRepository;

	@Autowired
	WorkScheduleRepository repository;

	public ApiResponse createWorkSchedule(WorkScheduleRequest request) {
		workScheduleRepository.save(request);
		return new ApiResponse(true, "Work schedule created successfully");

	public List<WorkSchedule> findAll() {
		return repository.findAll();
	}

	public WorkSchedule findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public WorkSchedule save(WorkSchedule workSchedule) {
		return repository.save(workSchedule);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public boolean isDoctorAvailable(User doctor, LocalDateTime appointmentTime) {
		// TODO: Implement actual logic
		return true;
	}

	public WorkSchedule findByDoctorAndTime(User doctor, LocalDateTime time) {
		// TODO: Thực hiện truy vấn để lấy WorkSchedule phù hợp với doctor và time
		return null; // hoặc trả về WorkSchedule phù hợp nếu có
	}
}
