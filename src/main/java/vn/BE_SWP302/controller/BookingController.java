package vn.BE_SWP302.controller;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.request.BookingRequest;
import vn.BE_SWP302.domain.response.BookingResponse;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.TreatmentServices;
import vn.BE_SWP302.service.BookingService;
import vn.BE_SWP302.service.UserService;
import vn.BE_SWP302.service.TreatmentServicesService;
import vn.BE_SWP302.domain.request.BookingFormRequest;
import vn.BE_SWP302.util.error.IdinvaliadException;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	BookingService bookingService;

	@Autowired
	UserService userService;

	@Autowired
	TreatmentServicesService treatmentServicesService;

	@GetMapping
	public ResponseEntity<List<BookingResponse>> getAll() {
		List<BookingResponse> responses = bookingService.findAll().stream().map(bookingService::toResponse).toList();
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookingResponse> get(@PathVariable Long id) {
		Booking booking = bookingService.findById(id);
		return ResponseEntity.ok(bookingService.toResponse(booking));
	}

	@PostMapping
	public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request) {
		// Lấy customer, doctor, service từ id
		User customer = userService.findById(request.getCustomerId());
		User doctor = userService.findById(request.getDoctorId());
		TreatmentServices service = treatmentServicesService.findById(request.getServiceId());
		Booking booking = bookingService.createBooking(customer, doctor, request.getAppointmentTime(), service);
		return ResponseEntity.ok(bookingService.toResponse(booking));
	}

	@PostMapping("/form")
	public ResponseEntity<BookingResponse> createFromForm(@RequestBody BookingFormRequest request)
			throws IdinvaliadException {
		// Ghép ngày + giờ thành LocalDateTime
		LocalDate date = LocalDate.parse(request.getDate());
		LocalTime time = LocalTime.parse(request.getTime());
		LocalDateTime appointmentTime = LocalDateTime.of(date, time);

		// Tìm doctor theo tên
		User doctor = userService.findByName(request.getDoctor());
		if (doctor == null)
			throw new IdinvaliadException("Bác sĩ không tồn tại");

		// Tìm service theo tên
		TreatmentServices service = treatmentServicesService.findByName(request.getService());
		if (service == null)
			throw new IdinvaliadException("Dịch vụ không tồn tại");

		// Lấy customer từ context đăng nhập hoặc truyền lên (ví dụ: lấy theo email,
		// hoặc tên)
		// Ở đây demo lấy theo tên
		User customer = userService.findByName(request.getCustomer());
		if (customer == null)
			throw new IdinvaliadException("Khách hàng không tồn tại");

		Booking booking = bookingService.createBooking(customer, doctor, appointmentTime, service);
		booking.setNote(request.getNotes());
		bookingService.save(booking);
		return ResponseEntity.ok(bookingService.toResponse(booking));
	}

	@PutMapping("/{id}")
	public Booking update(@PathVariable Long id, @RequestBody Booking booking) {
		booking.setBookingId(id);
		return bookingService.save(booking);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		bookingService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/doctors")
	public ResponseEntity<List<String>> getAllDoctorNames() {
		List<String> doctorNames = userService.getDoctorNames();
		return ResponseEntity.ok(doctorNames);
	}
}
