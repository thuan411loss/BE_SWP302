package vn.BE_SWP302.domain.response;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@AllArgsConstructor

public class ApiResponse {
    private boolean success;
    private String message;

}
