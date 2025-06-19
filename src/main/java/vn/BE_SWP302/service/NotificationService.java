package vn.BE_SWP302.service;

import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.Booking;
import vn.BE_SWP302.domain.User;

@Service
public class NotificationService {

	public void sendBookingNotification(User doctor, Booking savedBooking) {
		// TODO Auto-generated method stub

	}

	public void sendBookingStatusNotification(User user, Booking booking) {
		// TODO: Implement notification logic
	}

}
