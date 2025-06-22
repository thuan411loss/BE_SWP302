package vn.BE_SWP302.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.BE_SWP302.domain.TreatmentSchedules;
import vn.BE_SWP302.domain.dto.ApiResponse;
import vn.BE_SWP302.domain.dto.TreatmentScheduleRequest;
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

    @GetMapping("/result/{resultId}")
    public ResponseEntity<List<TreatmentSchedules>> getByResult(@PathVariable Long resultId) {
        return ResponseEntity.ok(treatmentSchedulesService.getSchedulesByResult(resultId));
    }
}