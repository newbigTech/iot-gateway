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
public class SysRoleUpdateDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;
    @ApiModelProperty("orgId")
    private Long orgId;
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
    @ApiModelProperty("roleName")
    private String roleName;
    @ApiModelProperty("resourceIds")
    private String resourceIds;
    @ApiModelProperty("orgIds")
    private String orgIds;

}
