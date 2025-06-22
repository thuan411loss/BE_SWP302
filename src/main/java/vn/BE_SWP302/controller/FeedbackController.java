package vn.BE_SWP302.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.dto.ApiResponse;
import vn.BE_SWP302.domain.dto.FeedbackRequest;
import vn.BE_SWP302.service.FeedbackService;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse> leaveFeedback(@RequestBody FeedbackRequest request) {
        return ResponseEntity.ok(feedbackService.createFeedback(request));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<FeedbackRequest>> getDoctorFeedback(@PathVariable Long doctorId) {
        return ResponseEntity.ok(feedbackService.getDoctorFeedback(doctorId));
    }
}
