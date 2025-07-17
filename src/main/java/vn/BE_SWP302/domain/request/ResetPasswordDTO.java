package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
    private String email;
    private String otp; // hoặc token
    private String newPassword;
}
