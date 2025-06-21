package vn.BE_SWP302.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T> {

    private int statusCode;
    private String error;
    private Object message;
    private T data;

}
