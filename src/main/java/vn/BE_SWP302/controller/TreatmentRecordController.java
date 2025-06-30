package vn.BE_SWP302.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.BE_SWP302.domain.request.TreatmentRecordRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.TreatmentRecordResponse;
import vn.BE_SWP302.service.TreatmentRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/treatment-records")
@RequiredArgsConstructor
public class TreatmentRecordController {

    private final TreatmentRecordService treatmentRecordService;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody TreatmentRecordRequest request) {
        return ResponseEntity.ok(treatmentRecordService.createTreatmentRecord(request));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<TreatmentRecordResponse>> getByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(treatmentRecordService.getRecordsByBooking(bookingId));
    }
}