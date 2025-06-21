package vn.BE_SWP302.service;

import javax.management.Notification;

import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.User;

@Service
public class NotificationService {
	private final NotificationRepository notificationRepository;

	public ApiResponse createNotification(NotificationRequest request) {
		Notification notification = new Notification();
		notification.setUserId(request.getUserId());
		notification.setMessage(request.getMessage());
		notification.setTimeStamp(new Date());
		notificationRepository.save(notification);
		return new ApiResponse(true,  "Notification created successfully ")

	public void sendBookingNotification(User doctor, Booking savedBooking) {
		// TODO Auto-generated method stub

	}

	public void sendBookingStatusNotification(User user, Booking booking) {
		// TODO: Implement notification logic
	}

}
