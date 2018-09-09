package com.newbig.im.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * User: Haibo
 * Date: 2018-05-01 10:05:30
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysUserVo {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("userId")
    private String userId;
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("password")
    private String password;
    @ApiModelProperty("roleId")
    private Long roleId;
    @ApiModelProperty("avatar")
    private String avatar;
    @ApiModelProperty("mobile")
    private String mobile;
    @ApiModelProperty("email")
    private String email;
    @ApiModelProperty("gmtCreate")
    private Date gmtCreate;
    @ApiModelProperty("gmtModify")
    private Date gmtModify;
    @ApiModelProperty("creator")
    private String creator;
    @ApiModelProperty("modifier")
    private String modifier;
    @ApiModelProperty("isDeleted")
    private Boolean isDeleted;
}
