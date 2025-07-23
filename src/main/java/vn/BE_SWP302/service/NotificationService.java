package vn.BE_SWP302.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.request.NotificationRequest;
import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.response.NotificationResponse;
import vn.BE_SWP302.domain.Notification; // Sửa lại import này cho đúng entity của bạn
import vn.BE_SWP302.repository.NotificationRepository; // Thêm import repository
import vn.BE_SWP302.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public ApiResponse createNotification(NotificationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(request.getMessage());
        notification.setTimeStamp(LocalDateTime.now());

        notificationRepository.save(notification);
        return new ApiResponse(true, "Notification created successfully");
    }

    public void sendBookingNotification(User doctor, Booking savedBooking) {
        // TODO Auto-generated method stub
    }

    public void sendBookingStatusNotification(User user, Booking booking) {
        // TODO: Implement notification logic
    }

    public List<NotificationResponse> getUserNotifications(Long userId) {
        return notificationRepository.findByUser_Id(userId).stream().map(n -> {
            NotificationResponse res = new NotificationResponse();
            res.setId(n.getId());
            res.setMessage(n.getMessage());
            res.setTimeStamp(n.getTimeStamp());
            return res;
        }).collect(Collectors.toList());
    }

}