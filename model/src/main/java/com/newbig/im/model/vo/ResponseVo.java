package com.newbig.im.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class ResponseVo<T> {
    private int code;
    private Boolean status;
    private String msg;
    private T result;

    public ResponseVo() {

    }

    public ResponseVo(int code, Boolean status, String msg, T result) {
        this.code = code;
        this.status = status;
        this.msg = msg;
        this.result = result;
    }

    public static ResponseVo success(Object o) {
        return new ResponseVo(200, Boolean.TRUE, null, o);
    }

    public static ResponseVo failure(String message) {
        return new ResponseVo(500, Boolean.FALSE, message, null);
    }
    public static ResponseVo failure(String message,String url) {
        return new ResponseVo(500, Boolean.FALSE, message, url);
    }
}
