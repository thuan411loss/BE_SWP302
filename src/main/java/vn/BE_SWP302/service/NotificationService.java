package vn.BE_SWP302.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Date;

import vn.BE_SWP302.domain.response.ApiResponse;
import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.User;
import vn.BE_SWP302.domain.request.NotificationRequest;
import vn.BE_SWP302.domain.Notification; // Sửa lại import này cho đúng entity của bạn
import vn.BE_SWP302.repository.NotificationRepository; // Thêm import repository
import vn.BE_SWP302.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    @Autowired
    private final UserRepository userRepository;

    public ApiResponse createNotification(NotificationRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            return new ApiResponse(false, "User not found");
        }
        Notification notification = new Notification();
        notification.setUser(user.get());
        notification.setMessage(request.getContent());
        notification.setTimeStamp(new Date());
        notificationRepository.save(notification);
        return new ApiResponse(true, "Notification created successfully");
    }

    public void sendBookingNotification(User doctor, Booking savedBooking) {
        // TODO Auto-generated method stub
    }

    public void sendBookingStatusNotification(User user, Booking booking) {
        // TODO: Implement notification logic
    }

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUser_Id(userId);
    }

}