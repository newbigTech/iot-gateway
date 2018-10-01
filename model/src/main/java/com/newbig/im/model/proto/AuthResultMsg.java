package com.newbig.im.model.proto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AuthResultMsg {
    private boolean success;
    private String msg;
}
