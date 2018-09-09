package com.newbig.im.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class LoginDto {
    @NotEmpty(message = "手机号不能为空")
    private String mobile;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, message = "密码错误")
    private String password;
}
