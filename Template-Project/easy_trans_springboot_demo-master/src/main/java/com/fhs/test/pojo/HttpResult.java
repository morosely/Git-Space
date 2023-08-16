package com.fhs.test.pojo;

import com.fhs.core.trans.vo.VO;
import lombok.Data;

import java.util.List;

@Data
public class HttpResult<T> {
    private static final Long version = 1L;

    private final Long xx = 1l;
    T data;
    List datas;
    VO oneData;
    int code = 200;
    String message = "hello";
    String ex;
    public HttpResult(T data){
        this.data = data;
    }

    public HttpResult() {
    }
}
