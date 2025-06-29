package vn.BE_SWP302.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeRoleDTO {
    @NotNull(message = "User ID không được để trống")
    private Long userId;

    @NotNull(message = "Role ID không được để trống")
    private Long roleId;
}