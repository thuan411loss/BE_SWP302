package vn.BE_SWP302.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.BE_SWP302.domain.Prescription;
import vn.BE_SWP302.domain.TreatmentSchedule;
import vn.BE_SWP302.domain.request.PrescriptionRequest;
import vn.BE_SWP302.domain.response.PrescriptionResponse;
import vn.BE_SWP302.repository.PrescriptionRepository;
import vn.BE_SWP302.repository.TreatmentSchedulesRepository;

import java.util.List;
import java.util.stream.Collectors;

//@Service
//@RequiredArgsConstructor
//public class PrescriptionService {
//
//    private final PrescriptionRepository prescriptionRepository;
//    private final TreatmentRecordRepository treatmentRecordRepository;
//
//    public ApiResponse createPrescription(PrescriptionRequest request) {
//        TreatmentRecord record = treatmentRecordRepository.findById(request.getTreatmentRecordId())
//                .orElseThrow(() -> new RuntimeException("Treatment Record not found"));
//
//        Prescription prescription = new Prescription();
//        prescription.setTreatmentRecord(record);
//        prescription.setMedicineName(request.getMedicineName());
//        prescription.setDosage(request.getDosage());
//        prescription.setInstruction(request.getInstructions());
//
//        prescriptionRepository.save(prescription);
//
//        return new ApiResponse(true, "Prescription created successfully");
//    }
//
//    public List<PrescriptionResponse> getPrescriptionsByRecord(Long recordId) {
//        return prescriptionRepository.findByTreatmentRecord_Id(recordId)
//                .stream()
//                .map(this::toResponse)
//                .collect(Collectors.toList());
//    }
//
//    private PrescriptionResponse toResponse(Prescription prescription) {
//        PrescriptionResponse response = new PrescriptionResponse();
//        response.setId(prescription.getPrescriptionId());
//        response.setMedicineName(prescription.getMedicineName());
//        response.setDosage(prescription.getDosage());
//        response.setInstructions(prescription.getInstruction());
//        return response;
//    }
//
//    public PrescriptionResponse getPrescriptionById(Long id) {
//        Prescription prescription = prescriptionRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Prescription not found"));
//        return toResponse(prescription);
//    }
//
//    public ApiResponse updatePrescription(Long id, PrescriptionRequest request) {
//        Prescription prescription = prescriptionRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Prescription not found"));
//        prescription.setMedicineName(request.getMedicineName());
//        prescription.setDosage(request.getDosage());
//        prescription.setInstruction(request.getInstructions());
//        // Nếu có các trường mới như prescribedDate, frequency, duration thì set thêm ở
//        // đây
//        prescriptionRepository.save(prescription);
//        return new ApiResponse(true, "Prescription updated successfully");
//    }
//
//    public ApiResponse deletePrescription(Long id) {
//        if (!prescriptionRepository.existsById(id)) {
//            return new ApiResponse(false, "Prescription not found");
//        }
//        prescriptionRepository.deleteById(id);
//        return new ApiResponse(true, "Prescription deleted successfully");
//    }
//
//    public List<PrescriptionResponse> getAllPrescriptions() {
//        return prescriptionRepository.findAll().stream()
//                .map(this::toResponse)
//                .collect(Collectors.toList());
//    }
//
//    public List<PrescriptionResponse> getPrescriptionsByCustomerId(Long customerId) {
//        // Lấy tất cả TreatmentRecord của customer này
//        List<TreatmentRecord> records = treatmentRecordRepository.findAll().stream()
//                .filter(r -> r.getBooking() != null && r.getBooking().getCustomer() != null &&
//                        r.getBooking().getCustomer().getId().equals(customerId))
//                .toList();
//        // Lấy tất cả Prescription thuộc các record này
//        return records.stream()
//                .flatMap(r -> r.getPrescriptions().stream())
//                .map(this::toResponse)
//                .toList();
//    }
//
//    public List<PrescriptionResponse> getPrescriptionsByResultId(Long resultId) {
//        return prescriptionRepository.findByMedicalResult_ResultId(resultId)
//                .stream()
//                .map(this::toResponse)
//                .collect(Collectors.toList());
//    }
//}
@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private TreatmentSchedulesRepository treatmentSchedulesRepository;

    public PrescriptionResponse createPrescription(PrescriptionRequest request) {
        TreatmentSchedule schedule = treatmentSchedulesRepository.findById(request.getTreatmentScheduleId())
                .orElseThrow(() -> new RuntimeException("Treatment Schedule not found"));

        Prescription prescription = new Prescription();
        prescription.setMedicineName(request.getMedicineName());
        prescription.setDosage(request.getDosage());
        prescription.setFrequency(request.getFrequency());
        prescription.setDuration(request.getDuration());
        prescription.setInstruction(request.getInstruction());
        prescription.setPrescribedDate(request.getPrescribedDate());
        prescription.setTreatmentSchedule(schedule);

        Prescription saved = prescriptionRepository.save(prescription);
        return toResponse(saved);
    }

    public List<PrescriptionResponse> getPrescriptionsBySchedule(Long scheduleId) {
        return prescriptionRepository.findByTreatmentSchedule_TreatmentScheduleId(scheduleId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deletePrescription(Long prescriptionId) {
        if (!prescriptionRepository.existsById(prescriptionId)) {
            throw new RuntimeException("Prescription not found");
        }
        prescriptionRepository.deleteById(prescriptionId);
    }

    private PrescriptionResponse toResponse(Prescription p) {
        PrescriptionResponse res = new PrescriptionResponse();
        res.setPrescriptionId(p.getPrescriptionId());
        res.setMedicineName(p.getMedicineName());
        res.setDosage(p.getDosage());
        res.setFrequency(p.getFrequency());
        res.setDuration(p.getDuration());
        res.setInstruction(p.getInstruction());
        res.setPrescribedDate(p.getPrescribedDate());
        res.setTreatmentScheduleId(p.getTreatmentSchedule().getTreatmentScheduleId());
        return res;
    }
}
