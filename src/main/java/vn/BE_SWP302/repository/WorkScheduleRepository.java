package vn.BE_SWP302.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.WorkSchedule;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {
    List<WorkSchedule> findByDoctor(User doctor);

    List<WorkSchedule> findByDoctorAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(User doctor, LocalDateTime start,
            LocalDateTime end);

}
