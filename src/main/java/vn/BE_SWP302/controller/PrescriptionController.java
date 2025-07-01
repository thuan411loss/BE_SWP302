package vn.BE_SWP302.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.BE_SWP302.domain.request.PrescriptionRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.PrescriptionResponse;
import vn.BE_SWP302.service.PrescriptionService;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody PrescriptionRequest request) {
        return ResponseEntity.ok(prescriptionService.createPrescription(request));
    }

    @GetMapping("/record/{recordId}")
    public ResponseEntity<List<PrescriptionResponse>> getByRecord(@PathVariable Long recordId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByRecord(recordId));
    }
}