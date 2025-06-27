package vn.BE_SWP302.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.BE_SWP302.domain.TreatmentRecord;

public interface TreatmentRecordRepository
        extends JpaRepository<TreatmentRecord, Long>, JpaSpecificationExecutor<TreatmentRecord> {
    List<TreatmentRecord> findByBooking_BookingId(Long bookingId);

}
