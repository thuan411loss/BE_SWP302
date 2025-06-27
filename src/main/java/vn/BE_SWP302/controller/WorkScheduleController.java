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
import vn.BE_SWP302.domain.request.WorkScheduleRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.WorkScheduleResponse;
import vn.BE_SWP302.service.WorkScheduleService;

@RestController
@RequestMapping("/api/work-schedules")
@RequiredArgsConstructor
public class WorkScheduleController {

    private final WorkScheduleService workScheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody WorkScheduleRequest request) {
        return ResponseEntity.ok(workScheduleService.create(request));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<WorkScheduleResponse>> getByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(workScheduleService.getByDoctor(doctorId));
    }
}
