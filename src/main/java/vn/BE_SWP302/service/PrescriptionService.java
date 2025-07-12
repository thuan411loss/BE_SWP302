package vn.BE_SWP302.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.BE_SWP302.domain.Prescription;
import vn.BE_SWP302.domain.TreatmentRecord;
import vn.BE_SWP302.domain.request.PrescriptionRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.PrescriptionResponse;
import vn.BE_SWP302.repository.PrescriptionRepository;
import vn.BE_SWP302.repository.TreatmentRecordRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final TreatmentRecordRepository treatmentRecordRepository;

    public ApiResponse createPrescription(PrescriptionRequest request) {
        TreatmentRecord record = treatmentRecordRepository.findById(request.getTreatmentRecordId())
                .orElseThrow(() -> new RuntimeException("Treatment Record not found"));

        Prescription prescription = new Prescription();
        prescription.setTreatmentRecord(record);
        prescription.setMedicineName(request.getMedicineName());
        prescription.setDosage(request.getDosage());
        prescription.setInstruction(request.getInstructions());

        prescriptionRepository.save(prescription);

        return new ApiResponse(true, "Prescription created successfully");
    }

    public List<PrescriptionResponse> getPrescriptionsByRecord(Long recordId) {
        return prescriptionRepository.findByTreatmentRecord_Id(recordId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private PrescriptionResponse toResponse(Prescription prescription) {
        PrescriptionResponse response = new PrescriptionResponse();
        response.setId(prescription.getPrescriptionId());
        response.setMedicineName(prescription.getMedicineName());
        response.setDosage(prescription.getDosage());
        response.setInstructions(prescription.getInstruction());
        return response;
    }

    public PrescriptionResponse getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        return toResponse(prescription);
    }

    public ApiResponse updatePrescription(Long id, PrescriptionRequest request) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        prescription.setMedicineName(request.getMedicineName());
        prescription.setDosage(request.getDosage());
        prescription.setInstruction(request.getInstructions());
        // Nếu có các trường mới như prescribedDate, frequency, duration thì set thêm ở
        // đây
        prescriptionRepository.save(prescription);
        return new ApiResponse(true, "Prescription updated successfully");
    }

    public ApiResponse deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            return new ApiResponse(false, "Prescription not found");
        }
        prescriptionRepository.deleteById(id);
        return new ApiResponse(true, "Prescription deleted successfully");
    }

    public List<PrescriptionResponse> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<PrescriptionResponse> getPrescriptionsByCustomerId(Long customerId) {
        // Lấy tất cả TreatmentRecord của customer này
        List<TreatmentRecord> records = treatmentRecordRepository.findAll().stream()
                .filter(r -> r.getBooking() != null && r.getBooking().getCustomer() != null &&
                        r.getBooking().getCustomer().getId().equals(customerId))
                .toList();
        // Lấy tất cả Prescription thuộc các record này
        return records.stream()
                .flatMap(r -> r.getPrescriptions().stream())
                .map(this::toResponse)
                .toList();
    }

    public List<PrescriptionResponse> getPrescriptionsByResultId(Long resultId) {
        return prescriptionRepository.findByMedicalResult_ResultId(resultId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}