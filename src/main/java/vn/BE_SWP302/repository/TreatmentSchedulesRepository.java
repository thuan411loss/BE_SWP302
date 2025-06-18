package vn.BE_SWP302.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.TreatmentSchedules;

@Repository
public interface TreatmentSchedulesRepository extends JpaRepository<TreatmentSchedules, Long> {
}
