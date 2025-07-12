package vn.BE_SWP302.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.BE_SWP302.domain.Prescription;

import java.util.List;

public interface PrescriptionRepository
        extends JpaRepository<Prescription, Long>, JpaSpecificationExecutor<Prescription> {
    List<Prescription> findByTreatmentRecord_Id(Long treatmentRecordId);

    List<Prescription> findByMedicalResult_ResultId(Long resultId);
}