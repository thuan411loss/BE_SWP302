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

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.request.TreatmentScheduleRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.TreatmentScheduleResponse;
import vn.BE_SWP302.service.TreatmentSchedulesService;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
class TreatmentScheduleController {

    private final TreatmentSchedulesService treatmentSchedulesService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createSchedule(@RequestBody TreatmentScheduleRequest request) {
        return ResponseEntity.ok(treatmentSchedulesService.createSchedule(request));
    }

    @GetMapping
    public ResponseEntity<List<TreatmentScheduleResponse>> getAll() {
        return ResponseEntity.ok(treatmentSchedulesService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentScheduleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(treatmentSchedulesService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody TreatmentScheduleRequest request) {
        return ResponseEntity.ok(treatmentSchedulesService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(treatmentSchedulesService.delete(id));
    }
}