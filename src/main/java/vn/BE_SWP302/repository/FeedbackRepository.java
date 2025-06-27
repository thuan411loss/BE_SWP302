package vn.BE_SWP302.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.BE_SWP302.domain.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>, JpaSpecificationExecutor<Feedback> {
    List<Feedback> findByDoctor_Id(Long doctorId);

}
