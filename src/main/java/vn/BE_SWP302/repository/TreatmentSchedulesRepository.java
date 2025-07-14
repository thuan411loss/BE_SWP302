package vn.BE_SWP302.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.BE_SWP302.domain.TreatmentSchedule;

@Repository
public interface TreatmentSchedulesRepository extends JpaRepository<TreatmentSchedule, Long> {
    List<TreatmentSchedule> findByMedicalResult_ResultId(Long resultId);

    List<TreatmentSchedule> findByMedicalResult_Examination_Booking_CustomerId(Long customerId);
}