package com.newbig.im.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
/**
 * User: Haibo
 * Date: 2018-07-01 11:49:28
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysRoleVo {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("orgId")
    private Long orgId;
    @ApiModelProperty("orgName")
    private String orgName;
    @ApiModelProperty("roleName")
    private String roleName;
    @ApiModelProperty("resourceIds")
    private String resourceIds;
    @ApiModelProperty("orgIds")
    private String orgIds;
    @ApiModelProperty("gmtCreate")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date gmtCreate;
    @ApiModelProperty("gmtModify")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date gmtModify;

}
