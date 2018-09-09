package com.newbig.im.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
/**
 * User: Haibo
 * Date: 2018-07-01 11:49:41
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysRoleAddDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;
    @ApiModelProperty("orgId")
    @NotNull(message = "orgId不能为空")
    private Long orgId;
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
    @ApiModelProperty("roleName")
    @NotEmpty(message = "roleName不能为空")
    private String roleName;
    @ApiModelProperty("resourceIds")
    @NotEmpty(message = "resourceIds不能为空")
    private String resourceIds;
    @ApiModelProperty("orgIds")
    @NotEmpty(message = "orgIds不能为空")
    private String orgIds;

}
