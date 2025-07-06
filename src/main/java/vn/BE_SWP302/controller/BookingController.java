package vn.BE_SWP302.controller;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.request.BookingRequest;
import vn.BE_SWP302.domain.response.BookingResponse;
import vn.BE_SWP302.domain.response.PatientDTO;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.TreatmentServices;
import vn.BE_SWP302.service.BookingService;
import vn.BE_SWP302.service.UserService;
import vn.BE_SWP302.service.TreatmentServicesService;
import vn.BE_SWP302.domain.request.BookingFormRequest;
import vn.BE_SWP302.util.SecurityUtil;
import vn.BE_SWP302.util.annotation.ApiMessage;
import vn.BE_SWP302.util.error.IdinvaliadException;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

	BookingService bookingService;
	UserService userService;
	TreatmentServicesService treatmentServicesService;

	@GetMapping
	@ApiMessage("Get all bookings")
	public ResponseEntity<List<BookingResponse>> getAll() {
		List<BookingResponse> responses = bookingService.findAll().stream().map(bookingService::toResponse).toList();
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/{id}")
	@ApiMessage("Get booking by ID")
	public ResponseEntity<BookingResponse> get(@PathVariable Long id) {
		Booking booking = bookingService.findById(id);
		return ResponseEntity.ok(bookingService.toResponse(booking));
	}

	@PostMapping
	@ApiMessage("Create a new booking")
	public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request) {
		// Lấy customer, doctor, service từ id
		User customer = userService.findById(request.getCustomerId());
		User doctor = userService.findById(request.getDoctorId());
		TreatmentServices service = treatmentServicesService.findById(request.getServiceId());
		Booking booking = bookingService.createBooking(customer, doctor, request.getAppointmentTime(), service);
		return ResponseEntity.ok(bookingService.toResponse(booking));
	}

	@PostMapping("/form")
	public ResponseEntity<?> createFromForm(@RequestBody BookingFormRequest request) {
		String email = SecurityUtil.getCurrentUserLogin().orElse("");
		User customer = userService.handleGetUserByUsername(email);
		if (customer == null)
			throw new IdinvaliadException("Khách hàng không tồn tại");

		User doctor = userService.findByName(request.getDoctor());
		if (doctor == null)
			throw new IdinvaliadException("Bác sĩ không tồn tại");

		TreatmentServices service = treatmentServicesService.findByName(request.getService());
		if (service == null)
			throw new IdinvaliadException("Dịch vụ không tồn tại");

		LocalDate date = LocalDate.parse(request.getDate());
		LocalTime time = LocalTime.parse(request.getTime());
		LocalDateTime appointmentTime = LocalDateTime.of(date, time);

		Booking booking = bookingService.createBooking(customer, doctor, appointmentTime, service);
		booking.setNote(request.getNotes());
		bookingService.save(booking);

		return ResponseEntity.ok(bookingService.toResponse(booking));
	}

	@PutMapping("/{id}")
	@ApiMessage("Update a booking")
	public Booking update(@PathVariable Long id, @RequestBody Booking booking) {
		booking.setBookingId(id);
		return bookingService.save(booking);
	}

	@DeleteMapping("/{id}")
	@ApiMessage("Delete a booking")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		bookingService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/doctors")
	@ApiMessage("Get all doctor names")
	public ResponseEntity<List<String>> getAllDoctorNames() {
		List<String> doctorNames = userService.getDoctorNames();
		return ResponseEntity.ok(doctorNames);
	}

	@GetMapping("/patients/{doctorId}")
	@ApiMessage("Get all patients by doctor ID")
	public ResponseEntity<List<PatientDTO>> getPatientsByDoctorId(@PathVariable Long doctorId) {
		List<PatientDTO> patients = bookingService.getPatientsByDoctorId(doctorId);
		return ResponseEntity.ok(patients);
	}

	@GetMapping("/customer/{customerId}")
	@ApiMessage("Get bookings by customer ID")
	public ResponseEntity<List<BookingResponse>> getBookingsByCustomerId(@PathVariable Long customerId) {
		List<BookingResponse> bookings = bookingService.getBookingsByCustomerId(customerId)
				.stream()
				.map(bookingService::toResponse)
				.toList();
		return ResponseEntity.ok(bookings);
	}
}
