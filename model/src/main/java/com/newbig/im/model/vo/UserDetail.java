package com.newbig.im.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User: haibo
 * Date: 2018/1/17 下午4:42
 * Desc:
 */
@Setter
@Getter
@ToString
public class UserDetail {
    @ApiModelProperty("用户基本信息")
    private UserVo user;
    @ApiModelProperty("用户角色")
    private RoleVo role;
}
