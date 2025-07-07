package vn.BE_SWP302.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.BE_SWP302.domain.request.PrescriptionRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.PrescriptionResponse;
import vn.BE_SWP302.service.PrescriptionService;
import vn.BE_SWP302.util.annotation.ApiMessage;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping("/create")
    @ApiMessage("Create a new prescription")
    public ResponseEntity<ApiResponse> create(@RequestBody PrescriptionRequest request) {
        return ResponseEntity.ok(prescriptionService.createPrescription(request));
    }

    @GetMapping("/record/{recordId}")
    @ApiMessage("Get prescriptions by record ID")
    public ResponseEntity<List<PrescriptionResponse>> getByRecord(@PathVariable Long recordId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByRecord(recordId));
    }

    @GetMapping("/{id}")
    @ApiMessage("Get prescription by ID")
    public ResponseEntity<PrescriptionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionById(id));
    }

    @PutMapping("/{id}")
    @ApiMessage("Update prescription")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody PrescriptionRequest request) {
        return ResponseEntity.ok(prescriptionService.updatePrescription(id, request));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete prescription")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(prescriptionService.deletePrescription(id));
    }

    @GetMapping
    @ApiMessage("Get all prescriptions")
    public ResponseEntity<List<PrescriptionResponse>> getAll() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    @GetMapping("/customer/{customerId}")
    @ApiMessage("Get prescriptions by customer ID")
    public ResponseEntity<List<PrescriptionResponse>> getByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByCustomerId(customerId));
    }

    @GetMapping("/result/{resultId}")
    @ApiMessage("Get prescriptions by result ID")
    public ResponseEntity<List<PrescriptionResponse>> getByResultId(@PathVariable Long resultId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByResultId(resultId));
    }

}