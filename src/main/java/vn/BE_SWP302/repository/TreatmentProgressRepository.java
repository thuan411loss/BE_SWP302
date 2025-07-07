package vn.BE_SWP302.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.TreatmentProgress;

@Repository
public interface TreatmentProgressRepository extends JpaRepository<TreatmentProgress, Long>,
        JpaSpecificationExecutor<TreatmentProgress> {
    List<TreatmentProgress> findByTreatmentSchedule_ScheduleId(Long scheduleId);

    @Query("SELECT COUNT(tp) FROM TreatmentProgress tp WHERE tp.status = 'ONGOING'")
    long countOngoing();

    @Query("""
                SELECT tp FROM TreatmentProgress tp
                JOIN tp.treatmentSchedule ts
                JOIN ts.medicalResult mr
                JOIN mr.treatmentRecords tr
                JOIN tr.booking b
                WHERE b.customer.id = :customerId
            """)
    List<TreatmentProgress> findByCustomerId(@Param("customerId") Long customerId);

}
