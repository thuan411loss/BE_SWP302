package vn.BE_SWP302.domain.response;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import vn.BE_SWP302.util.constant.GenderEnum;

@Getter
@Setter
public class ResAdminUserDTO {
    private long id;
    private String name;
    private String email;
    private GenderEnum gender;
    private String address;
    private String phone;
    private int age;
    private Instant createdAt;
    private Instant updatedAt;

    // Thông tin role cho admin
    private String roleName;
    private Long roleId;

    // Thông tin bổ sung cho admin
    private String createdBy;
    private String updatedBy;
}