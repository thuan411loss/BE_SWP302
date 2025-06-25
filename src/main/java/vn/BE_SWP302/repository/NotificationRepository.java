package vn.BE_SWP302.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.BE_SWP302.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Thêm các phương thức truy vấn tùy chỉnh nếu cần
    List<Notification> findByUser_Id(Long userId);
}
