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
// import io.swagger.v3.oas.annotations.parameters.RequestBody;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
import vn.BE_SWP302.domain.Examination;
import vn.BE_SWP302.domain.request.ExaminationRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.ExaminationResponse;
import vn.BE_SWP302.service.ExaminationService;

@RestController
@RequestMapping("/api/examinations")
@RequiredArgsConstructor
public class ExaminationController {

    private final ExaminationService examinationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createExamination(
            @RequestBody ExaminationRequest request) {
        return ResponseEntity.ok(examinationService.createExamination(request));
    }

    @GetMapping
    public ResponseEntity<List<ExaminationResponse>> getAllExaminations() {
        return ResponseEntity.ok(examinationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Examination> getExamination(@PathVariable Long id) {
        return ResponseEntity.ok(examinationService.findById(id));
    }
}
