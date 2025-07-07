package vn.BE_SWP302.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.Examination;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {
    List<Examination> findByBooking_BookingId(Long bookingId);

    @Query("SELECT e FROM Examination e WHERE e.booking.customer.customerId = :customerId")
    List<Examination> findByCustomerId(@Param("customerId") Long customerId);
}
