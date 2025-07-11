package vn.BE_SWP302.domain.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.BE_SWP302.util.constant.GenderEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUserDTO {

    private long id;
    private String email;
    private String name;
    private GenderEnum gender;
    private String address;
    private String phone;
    private int age;
    private Instant updatedAt;
    private Instant createdAt;

}
