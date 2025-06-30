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
import vn.BE_SWP302.domain.MedicalResults;
import vn.BE_SWP302.domain.request.MedicalResultsRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.service.MedicalResultsService;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor

public class MedicalResultsController {

    private final MedicalResultsService medicalResultsService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createMedicalResults(@RequestBody MedicalResultsRequest request) {
        return ResponseEntity.ok(medicalResultsService.createMedicalResults(request));
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<MedicalResults>> getByExam(@PathVariable Long examId) {
        return ResponseEntity.ok(medicalResultsService.getResultsByExamId(examId));
    }
}
