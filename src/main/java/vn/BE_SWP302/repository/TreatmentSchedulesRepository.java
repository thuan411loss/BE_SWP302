package vn.BE_SWP302.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.BE_SWP302.domain.TreatmentSchedules;

@Repository
public interface TreatmentSchedulesRepository extends JpaRepository<TreatmentSchedules, Long> {
    List<TreatmentSchedules> findByMedicalResult_ResultId(Long resultId);

    List<TreatmentSchedules> findByMedicalResult_Examination_Booking_CustomerId(Long customerId);
}