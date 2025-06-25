package vn.BE_SWP302.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;<<<<<<<HEAD
import vn.BE_SWP302.domain.Feedback;
import vn.BE_SWP302.domain.Invoice;
import vn.BE_SWP302.domain.dto.ApiResponse;
import vn.BE_SWP302.domain.dto.FeedbackRequest;

import java.util.ArrayList;
import java.util.Date;

import vn.BE_SWP302.repository.FeedbackRepository;
import vn.BE_SWP302.repository.InvoiceRepository;=======
import vn.BE_SWP302.domain.Invoice;>>>>>>>52e7f 7 a56656db377c4bc8fb9da1e94e00aa7b80

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;<<<<<<<HEAD
    private final InvoiceRepository invoiceRepository;=======>>>>>>>52e7f 7 a56656db377c4bc8fb9da1e94e00aa7b80

    public ApiResponse createFeedback(FeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setUserId(request.getUserId());
        feedback.setRating(request.getRating());

        feedback.setMessage(request.getComment());
=======
        feedback.setMessage(request.getMessage());

        feedback.setCreatedDate(new Date());
        feedbackRepository.save(feedback);
        return new ApiResponse(true, "Feedback created successfully");
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public List<Invoice> getInvoicesByUser(Long userId) {
        return invoiceRepository.findByBooking_Customer_Id(userId);
    }

    public List<FeedbackRequest> getDoctorFeedback(Long doctorId) {
        // TODO: Implement actual logic to fetch feedback for the doctor
        return new ArrayList<>();
    }=======

}
