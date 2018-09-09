package com.newbig.im.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * User: Haibo
 * Date: 2018-05-01 10:01:11
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysUserRoleVo {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("userId")
    private String userId;
    @ApiModelProperty("roleId")
    private String roleId;
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
    @ApiModelProperty("privilegeOrgIds")
    private String privilegeOrgIds;
}
