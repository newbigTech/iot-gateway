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
public class ResourceVo {
    @ApiModelProperty("资源id")
    private Long id;
    @ApiModelProperty("前端菜单URL")
    private String uri;
    @ApiModelProperty("前端菜单名称")
    private String name;
    @ApiModelProperty("前端菜单icon")
    private String icon;
    @ApiModelProperty("子菜单")
    private List<ResourceVo> children;
}
