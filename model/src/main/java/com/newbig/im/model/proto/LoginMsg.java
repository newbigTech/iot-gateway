package com.newbig.im.model.proto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LoginMsg {
    private Integer type; //id类型 userId 手机号 设备序列号
    private Long id;
    private String token;
}
