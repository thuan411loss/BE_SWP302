package vn.BE_SWP302.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.dto.TreatmentProgressRequest;
import vn.BE_SWP302.domain.dto.TreatmentProgressResponse;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.service.TreatmentProgressService;

@RestController
@RequestMapping("/api/treatment-progress")
@RequiredArgsConstructor
public class TreatmentProgressController {

    private final TreatmentProgressService treatmentProgressService = null;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody TreatmentProgressRequest request) {
        return ResponseEntity.ok(treatmentProgressService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<TreatmentProgressResponse>> getAll() {
        return ResponseEntity.ok(treatmentProgressService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentProgressResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(treatmentProgressService.getById(id));
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<TreatmentProgressResponse>> getBySchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(treatmentProgressService.getBySchedule(scheduleId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody TreatmentProgressRequest request) {
        return ResponseEntity.ok(treatmentProgressService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(treatmentProgressService.delete(id));
    }
}