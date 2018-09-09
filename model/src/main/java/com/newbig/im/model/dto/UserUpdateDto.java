package com.newbig.im.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * User: Haibo
 * Date: 2018-01-22 20:48:10
 * Desc:
 */
@Setter
@Getter
@ToString
public class UserUpdateDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
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
    @ApiModelProperty("gmtModifier")
    private String gmtModifier;
    @ApiModelProperty("isDeleted")
    private Boolean isDeleted;

}
