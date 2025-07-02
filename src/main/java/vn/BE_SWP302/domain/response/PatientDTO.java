package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {
    private Long bookingId;
    private String name;
    private int age;
    private String phone;

}
