package vn.BE_SWP302.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {

    private int page;
    private int pageSize;
    private int pages;
    private long total;

}
