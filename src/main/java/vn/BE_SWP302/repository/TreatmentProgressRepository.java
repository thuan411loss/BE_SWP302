package vn.BE_SWP302.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.TreatmentProgress;

@Repository
public interface TreatmentProgressRepository extends JpaRepository<TreatmentProgress, Long> {
    List<TreatmentProgress> findByTreatmentSchedule_ScheduleId(Long scheduleId);

}
