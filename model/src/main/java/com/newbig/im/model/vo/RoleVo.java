package com.newbig.im.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * User: haibo
 * Date: 2018/1/17 下午4:53
 * Desc:
 */
@Setter
@Getter
@ToString
public class RoleVo {
    @ApiModelProperty("角色id")
    private Long id;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("资源权限")
    private List<ResourceVo> resources;
}
