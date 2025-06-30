package vn.BE_SWP302.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import vn.BE_SWP302.util.error.IdinvaliadException;

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
        Examination examination = examinationService.findById(id);
        if (examination == null) {
            return ResponseEntity.notFound().build();
        }

        ExaminationResponse response = new ExaminationResponse();
        response.setExamId(examination.getExamId());
        response.setExamDate(examination.getExamDate());
        response.setDiagnosis(examination.getDiagnosis());
        response.setRecommendation(examination.getRecommendation());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/booking/{bookingId}")
    @ApiMessage("Get examination by booking ID")
    public ResponseEntity<List<ExaminationResponse>> getExaminationsByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(examinationService.findByBookingId(bookingId));
    }
}
