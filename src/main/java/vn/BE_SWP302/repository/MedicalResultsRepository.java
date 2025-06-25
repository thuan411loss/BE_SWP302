package vn.BE_SWP302.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.BE_SWP302.domain.MedicalResults;

@Repository
public interface MedicalResultsRepository extends JpaRepository<MedicalResults, Long> {

    List<MedicalResults> findByExamination_ExamId(Long examId);
}
