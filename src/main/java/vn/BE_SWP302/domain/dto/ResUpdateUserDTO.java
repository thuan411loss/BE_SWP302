package vn.BE_SWP302.domain.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import vn.BE_SWP302.util.constant.GenderEnum;

@Getter
@Setter
public class ResUpdateUserDTO {
    private long id;
    private String name;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant updatedAt;
}
