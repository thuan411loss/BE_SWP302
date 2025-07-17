package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;
import vn.BE_SWP302.util.constant.GenderEnum;

@Getter
@Setter
public class CreateUserAdminDTO {
    private String name;
    private String email;
    private String password;
    private int age;
    private String address;
    private String phone;
    private GenderEnum gender;
    private Long roleId; // hoặc roleName nếu muốn
    private String roleName;
}
