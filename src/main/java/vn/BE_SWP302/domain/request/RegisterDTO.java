package vn.BE_SWP302.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import vn.BE_SWP302.util.constant.GenderEnum;

@Getter
@Setter
public class RegisterDTO {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    private int age;
    private String address;
    @NotNull(message = "Giới tính không được để trống")
    private GenderEnum gender; // hoặc GenderEnum nếu muốn
}