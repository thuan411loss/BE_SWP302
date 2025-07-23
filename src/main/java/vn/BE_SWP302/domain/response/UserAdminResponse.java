package vn.BE_SWP302.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdminResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
}
