package vn.BE_SWP302.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Feedback;
import vn.BE_SWP302.domain.Invoice;
import vn.BE_SWP302.domain.dto.ApiResponse;
import vn.BE_SWP302.domain.dto.FeedbackRequest;

import java.util.ArrayList;
import java.util.Date;

import vn.BE_SWP302.repository.FeedbackRepository;
import vn.BE_SWP302.repository.InvoiceRepository;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final InvoiceRepository invoiceRepository;

    public ApiResponse createFeedback(FeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setUserId(request.getUserId());
        feedback.setRating(request.getRating());
        feedback.setMessage(request.getComment());
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
    }

}
