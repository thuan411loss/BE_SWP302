package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class NotificationResponse {
    private Long id;
    private String message;
    private LocalDateTime timeStamp;
}