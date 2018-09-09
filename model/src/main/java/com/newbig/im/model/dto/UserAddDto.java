package com.newbig.im.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
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
public class UserAddDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;
    @ApiModelProperty("userId")
    @NotEmpty(message = "userId不能为空")
    private String userId;
    @ApiModelProperty("name")
    @NotEmpty(message = "name不能为空")
    private String name;
    @ApiModelProperty("password")
    @NotEmpty(message = "password不能为空")
    private String password;
    @ApiModelProperty("roleId")
    @NotNull(message = "roleId不能为空")
    private Long roleId;
    @ApiModelProperty("avatar")
    @NotEmpty(message = "avatar不能为空")
    private String avatar;
    @ApiModelProperty("mobile")
    @NotEmpty(message = "mobile不能为空")
    private String mobile;
    @ApiModelProperty("email")
    @NotEmpty(message = "email不能为空")
    private String email;
    @ApiModelProperty("gmtCreate")
    @NotNull(message = "gmtCreate不能为空")
    private Date gmtCreate;
    @ApiModelProperty("gmtModify")
    @NotNull(message = "gmtModify不能为空")
    private Date gmtModify;
    @ApiModelProperty("creator")
    @NotEmpty(message = "creator不能为空")
    private String creator;
    @ApiModelProperty("gmtModifier")
    @NotEmpty(message = "gmtModifier不能为空")
    private String gmtModifier;
    @ApiModelProperty("isDeleted")
    @NotNull(message = "isDeleted不能为空")
    private Boolean isDeleted;

}
