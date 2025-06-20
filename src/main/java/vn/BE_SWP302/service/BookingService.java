package vn.BE_SWP302.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.TreatmentServices;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.WorkSchedule;
import vn.BE_SWP302.repository.BookingRepository;
import vn.BE_SWP302.util.constant.BookingStatus;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private WorkScheduleService workScheduleService;

	@Autowired
	private NotificationService notificationService;

	public Booking createBooking(User customer, User doctor, LocalDateTime appointmentTime, TreatmentServices service) {
		// Check if doctor is available at the requested time
		if (!workScheduleService.isDoctorAvailable(doctor, appointmentTime)) {
			throw new RuntimeException("Doctor is not available at the requested time");
		}

		// Lấy work schedule phù hợp với bác sĩ và thời gian
		WorkSchedule work = workScheduleService.findByDoctorAndTime(doctor, appointmentTime);

		// Tạo booking và set các trường cần thiết
		Booking booking = new Booking();
		booking.setCustomer(customer);
		booking.setService(service);
		booking.setWork(work);
		booking.setBookingDate(appointmentTime);
		booking.setStatus("Pending"); // hoặc trạng thái khác nếu muốn

		// Lưu booking
		Booking savedBooking = bookingRepository.save(booking);

		// Send notification to doctor
		notificationService.sendBookingNotification(doctor, savedBooking);

		return savedBooking;
	}

	public List<Booking> getBookingsByCustomer(User customer) {
		return bookingRepository.findByCustomerOrderByBookingDateDesc(customer);
	}

	public List<Booking> getBookingsByDoctor(User doctor) {
		return bookingRepository.findByWork_Doctor(doctor);
	}

	public Optional<Booking> getBookingById(Long id) {
		return bookingRepository.findById(id);
	}

	public Booking updateBookingStatus(Long bookingId, BookingStatus status) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found"));

		booking.setStatus(status.name());
		Booking updatedBooking = bookingRepository.save(booking);

		// Send notification to customer
		notificationService.sendBookingStatusNotification(booking.getCustomer(), updatedBooking);

		return updatedBooking;
	}

	public List<Booking> getDoctorBookingsForTimeRange(User doctor, LocalDateTime startTime, LocalDateTime endTime) {
		return bookingRepository.findByWork_DoctorAndBookingDateBetween(doctor, startTime, endTime);
	}

	public List<Booking> findAll() {
		return bookingRepository.findAll();
	}

	public Booking findById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findById'");
	}

	public Booking save(Booking booking) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'save'");
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}
}
