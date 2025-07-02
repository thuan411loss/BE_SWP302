package vn.BE_SWP302.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

	List<Booking> findByCustomerOrderByBookingDateDesc(User customer);

	List<Booking> findByWork_Doctor(User doctor);

	long countByBookingDateBetween(LocalDateTime start, LocalDateTime end);

	List<Booking> findByWork_DoctorAndBookingDateBetween(User doctor, LocalDateTime start, LocalDateTime end);

	List<Booking> findByWork_DoctorAndBookingDate(User doctor, LocalDateTime bookingDate);
}
