package vn.BE_SWP302.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.request.MedicalResultsRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.MedicalResultResponse;
import vn.BE_SWP302.service.MedicalResultsService;
import vn.BE_SWP302.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor

public class MedicalResultsController {

    private final MedicalResultsService medicalResultsService;

    @PostMapping("/add")
    @ApiMessage("Create new medical result")
    public ResponseEntity<ApiResponse> createMedicalResults(@RequestBody MedicalResultsRequest request) {
        return ResponseEntity.ok(medicalResultsService.createMedicalResults(request));
    }

    @GetMapping("/exam/{examId}")
    @ApiMessage("Get medical results by exam ID")
    public ResponseEntity<List<MedicalResultResponse>> getByExam(@PathVariable Long examId) {
        return ResponseEntity.ok(medicalResultsService.getByExam(examId));
    }

    @GetMapping("/customer/{customerId}")
    @ApiMessage("Get medical results by customer ID")
    public ResponseEntity<List<MedicalResultResponse>> getByCustomerId(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(medicalResultsService.getByCustomerId(customerId));
    }

    @GetMapping("/{resultId}")
    @ApiMessage("Get medical result by ID")
    public ResponseEntity<MedicalResultResponse> getById(@PathVariable Long resultId) {
        return ResponseEntity.ok(medicalResultsService.getById(resultId));
    }
}
