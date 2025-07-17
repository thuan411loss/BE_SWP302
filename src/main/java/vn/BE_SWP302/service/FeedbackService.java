package vn.BE_SWP302.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.BE_SWP302.domain.Feedback;
import vn.BE_SWP302.domain.Invoice;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.request.FeedbackRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.FeedbackResponse;

import java.util.stream.Collectors;

import vn.BE_SWP302.repository.FeedbackRepository;
import vn.BE_SWP302.repository.InvoiceRepository;
import vn.BE_SWP302.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;

    public ApiResponse createFeedback(FeedbackRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setDoctor(doctor);
        feedback.setRating(request.getRating());
        feedback.setComment(request.getComment());
        feedback.setCreatedDate(LocalDateTime.now());

        feedbackRepository.save(feedback);
        return new ApiResponse(true, "Feedback submitted successfully");
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public List<Invoice> getInvoicesByUser(Long userId) {
        return invoiceRepository.findByBooking_Customer_Id(userId);
    }

    public List<FeedbackResponse> getDoctorFeedback(Long doctorId) {
        return feedbackRepository.findByDoctor_Id(doctorId).stream().map(fb -> {
            FeedbackResponse response = new FeedbackResponse();
            response.setFeedbackId(fb.getFeedbackId());
            response.setUserId(fb.getUser().getId());
            response.setDoctorId(fb.getDoctor().getId());
            response.setRating(fb.getRating());
            response.setComment(fb.getComment());
            response.setCreatedDate(fb.getCreatedDate());
            return response;
        }).collect(Collectors.toList());
    }

}
