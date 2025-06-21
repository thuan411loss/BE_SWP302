package vn.BE_SWP302.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Invoice;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public ApiResponse createFeedback(FeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setUserId(request.getUserId());
        feedback.setRating(request.getRating());
        feedback.setMessage(request.getMessage());
        feedback.setCreatedDate(new Date());
        feedbackRepository.save(feedback);
        return new ApiResponse(true, "Feedback created successfully");
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }
    public List<Invoice> getInvoicesByUser(Long userId) {
        return invoiceRepository.findByUserId(userId);}

}
