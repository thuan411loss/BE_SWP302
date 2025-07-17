package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingFormRequest {
    private String date; // yyyy-MM-dd
    private String time; // HH:mm
    private String doctor; // tên bác sĩ
    private String service; // tên dịch vụ
    private String customer; // tên khách hàng
    private String notes; // ghi chú
}