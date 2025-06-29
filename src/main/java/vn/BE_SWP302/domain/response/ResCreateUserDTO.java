package vn.BE_SWP302.domain.response;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import vn.BE_SWP302.domain.enums.GenderEnum;


@Getter
@Setter
public class ResCreateUserDTO {
    private long id;
    private String name;
    private String email;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant createdAt;

}
