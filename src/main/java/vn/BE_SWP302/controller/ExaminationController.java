package vn.BE_SWP302.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
// import io.swagger.v3.oas.annotations.parameters.RequestBody;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
import vn.BE_SWP302.domain.Examination;
import vn.BE_SWP302.domain.request.ExaminationRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.ExaminationResponse;
import vn.BE_SWP302.service.ExaminationService;
import vn.BE_SWP302.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1/examinations")
@RequiredArgsConstructor
public class ExaminationController {

    private final ExaminationService examinationService;

    @PostMapping
    @ApiMessage("Create examination")
    public ResponseEntity<ApiResponse> createExamination(@Valid @RequestBody ExaminationRequest request) {
        return ResponseEntity.ok(examinationService.createExamination(request));
    }

    @GetMapping
    @ApiMessage("Get all examinations")
    public ResponseEntity<List<ExaminationResponse>> getAllExaminations() {
        return ResponseEntity.ok(examinationService.findAll());
    }

    @GetMapping("/{id}")
    @ApiMessage("Get examination by ID")
    public ResponseEntity<ExaminationResponse> getExamination(@PathVariable Long id) {
        ExaminationResponse response = examinationService.findByIdResponse(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/booking/{bookingId}")
    @ApiMessage("Get examination by booking ID")
    public ResponseEntity<List<ExaminationResponse>> getExaminationsByBooking(
            @PathVariable("bookingId") Long bookingId) {
        return ResponseEntity.ok(examinationService.findByBookingId(bookingId));
    }

    @GetMapping("/customer/{customerId}")
    @ApiMessage("Get examinations by customer ID")
    public ResponseEntity<List<ExaminationResponse>> getExaminationsByCustomer(
            @PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(examinationService.findByCustomerId(customerId));
    }

    @PutMapping("/{id}")
    @ApiMessage("Update examination")
    public ResponseEntity<ApiResponse> updateExamination(@PathVariable Long id,
            @Valid @RequestBody ExaminationRequest request) {
        Examination examination = examinationService.findById(id);
        if (examination == null) {
            return ResponseEntity.notFound().build();
        }

        examination.setDiagnosis(request.getDiagnosis());
        examination.setRecommendation(request.getRecommendation());

        examinationService.save(examination);
        return ResponseEntity.ok(new ApiResponse(true, "Examination updated successfully"));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete examination")
    public ResponseEntity<ApiResponse> deleteExamination(@PathVariable Long id) {
        Examination examination = examinationService.findById(id);
        if (examination == null) {
            return ResponseEntity.notFound().build();
        }

        examinationService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Examination deleted successfully"));
    }

}
