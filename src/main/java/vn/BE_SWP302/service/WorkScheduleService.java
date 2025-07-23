package vn.BE_SWP302.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.WorkSchedule;
import vn.BE_SWP302.domain.request.WorkScheduleRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.WorkScheduleResponse;
import vn.BE_SWP302.repository.BookingRepository;
import vn.BE_SWP302.repository.UserRepository;
import vn.BE_SWP302.repository.WorkScheduleRepository;

@Service
@RequiredArgsConstructor
public class WorkScheduleService {

	private final WorkScheduleRepository workScheduleRepository;
	private final UserRepository userRepository;
	private final BookingRepository bookingRepository;

	public ApiResponse create(WorkScheduleRequest request) {
		Optional<User> doctorOpt = userRepository.findById(request.getUserId());

		if (doctorOpt.isEmpty())
			return new ApiResponse(false, "User not found");

		User user = doctorOpt.get();
		if (user.getRole() == null || !"Doctor".equalsIgnoreCase(user.getRole().getRoleName())) {
			return new ApiResponse(false, "User is not a doctor");
		}

		WorkSchedule workSchedule = new WorkSchedule();
		workSchedule.setDoctor(user);
		workSchedule.setStartTime(request.getStartTime());
		workSchedule.setEndTime(request.getEndTime());
		workSchedule.setIsAvailable(true);
		workSchedule.setNotes(request.getNotes());

		workScheduleRepository.save(workSchedule);
		return new ApiResponse(true, "Work schedule created");
	}

	public List<WorkScheduleResponse> getByDoctor(Long doctorId) {
		Optional<User> doctorOpt = userRepository.findById(doctorId);
		if (doctorOpt.isEmpty())
			return List.of();

		return workScheduleRepository.findByDoctor(doctorOpt.get()).stream().map(ws -> {
			WorkScheduleResponse res = new WorkScheduleResponse();
			res.setWorkId(ws.getWorkId());
			res.setUserId(ws.getDoctor().getId());
			res.setDoctorName(ws.getDoctor().getName());
			res.setStartTime(ws.getStartTime());
			res.setEndTime(ws.getEndTime());
			res.setIsAvailable(ws.getIsAvailable());
			res.setNotes(ws.getNotes());
			return res;
		}).collect(Collectors.toList());
	}

	public boolean isDoctorAvailable(User doctor, LocalDateTime appointmentTime) {
		List<WorkSchedule> schedules = workScheduleRepository
				.findByDoctorAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
						doctor, appointmentTime, appointmentTime);
		return schedules.stream().anyMatch(WorkSchedule::getIsAvailable);
	}

	public WorkSchedule findByDoctorAndTime(User doctor, LocalDateTime time) {
		List<WorkSchedule> schedules = workScheduleRepository
				.findByDoctorAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(doctor, time, time);
		// Lấy lịch đầu tiên thỏa mãn (nếu có)
		return schedules.stream().findFirst().orElse(null);
	}

	public boolean isDoctorBooked(User doctor, LocalDateTime appointmentTime) {
		// Tìm tất cả booking của bác sĩ này tại thời điểm đó (trạng thái chưa bị hủy)
		List<Booking> bookings = bookingRepository.findByWork_DoctorAndBookingDate(doctor, appointmentTime);
		return !bookings.isEmpty();
	}

	public ApiResponse update(Long id, WorkScheduleRequest request) {
		Optional<WorkSchedule> wsOpt = workScheduleRepository.findById(id);
		if (wsOpt.isEmpty()) {
			return new ApiResponse(false, "Work schedule not found");
		}
		WorkSchedule ws = wsOpt.get();
		if (request.getUserId() != null) {
			Optional<User> doctorOpt = userRepository.findById(request.getUserId());
			if (doctorOpt.isPresent()) {
				ws.setDoctor(doctorOpt.get());
			}
		}
		if (request.getStartTime() != null) ws.setStartTime(request.getStartTime());
		if (request.getEndTime() != null) ws.setEndTime(request.getEndTime());
		if (request.getNotes() != null) ws.setNotes(request.getNotes());
		workScheduleRepository.save(ws);
		return new ApiResponse(true, "Work schedule updated");
	}

	public List<WorkScheduleResponse> getAll() {
		return workScheduleRepository.findAll().stream().map(ws -> {
			WorkScheduleResponse res = new WorkScheduleResponse();
			res.setWorkId(ws.getWorkId());
			res.setUserId(ws.getDoctor().getId());
			res.setDoctorName(ws.getDoctor().getName());
			res.setStartTime(ws.getStartTime());
			res.setEndTime(ws.getEndTime());
			res.setIsAvailable(ws.getIsAvailable());
			res.setNotes(ws.getNotes());
			return res;
		}).collect(Collectors.toList());
	}
}
