package vn.BE_SWP302.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.BE_SWP302.domain.MedicalResults;
import vn.BE_SWP302.domain.dto.ApiResponse;
import vn.BE_SWP302.domain.dto.MedicalResultsRequest;
import vn.BE_SWP302.service.MedicalResultsService;

@RestController
@RequestMapping("/api/results")
public class MedicalResultsController {

    private final MedicalResultsService medicalResultsService;

    public MedicalResultsController(MedicalResultsService medicalResultsService) {
        this.medicalResultsService = medicalResultsService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addResult(@RequestBody MedicalResultsRequest request) {
        return ResponseEntity.ok(medicalResultsService.addMedicalResult(request));
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<MedicalResults>> getByExam(@PathVariable Long examId) {
        return ResponseEntity.ok(medicalResultsService.getResultsByExamId(examId));
    }
}
