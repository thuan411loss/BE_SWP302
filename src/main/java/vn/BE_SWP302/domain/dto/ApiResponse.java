package vn.BE_SWP302.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ApiResponse {
    private boolean success;
    private String message;

}
