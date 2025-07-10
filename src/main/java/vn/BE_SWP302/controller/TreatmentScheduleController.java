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
@RequestMapping("/api/treatment-schedules")
@RequiredArgsConstructor
public class TreatmentScheduleController {

    private final TreatmentSchedulesService treatmentSchedulesService;

    @PostMapping("/create")
    public ResponseEntity<TreatmentScheduleResponse> create(@RequestBody TreatmentScheduleRequest request) {
        return ResponseEntity.ok(treatmentSchedulesService.createSchedule(request));
    }

    @GetMapping("/result/{resultId}")
    public ResponseEntity<List<TreatmentScheduleResponse>> getByResult(@PathVariable Long resultId) {
        return ResponseEntity.ok(treatmentSchedulesService.getSchedulesByResultId(resultId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentScheduleResponse> update(@PathVariable Long id, @RequestBody TreatmentScheduleRequest request) {
        return ResponseEntity.ok(treatmentSchedulesService.updateSchedule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        treatmentSchedulesService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}