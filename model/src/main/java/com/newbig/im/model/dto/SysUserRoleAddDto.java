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
 * Date: 2018-05-01 10:01:11
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysUserRoleAddDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;
    @ApiModelProperty("userId")
    @NotEmpty(message = "userId不能为空")
    private String userId;
    @ApiModelProperty("roleId")
    @NotEmpty(message = "roleId不能为空")
    private String roleId;
    @ApiModelProperty("gmtCreate")
    @NotNull(message = "gmtCreate不能为空")
    private Date gmtCreate;
    @ApiModelProperty("gmtModify")
    @NotNull(message = "gmtModify不能为空")
    private Date gmtModify;
    @ApiModelProperty("creator")
    @NotEmpty(message = "creator不能为空")
    private String creator;
    @ApiModelProperty("modifier")
    @NotEmpty(message = "modifier不能为空")
    private String modifier;
    @ApiModelProperty("isDeleted")
    @NotNull(message = "isDeleted不能为空")
    private Boolean isDeleted;
    @ApiModelProperty("privilegeOrgIds")
    @NotEmpty(message = "privilegeOrgIds不能为空")
    private String privilegeOrgIds;

}
