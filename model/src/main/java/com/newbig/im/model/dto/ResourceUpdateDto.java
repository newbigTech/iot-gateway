package com.newbig.im.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * User: haibo
 * Date: 2017/10/2 下午6:16
 * Desc:
 */
@Setter
@Getter
@ToString
public class ResourceUpdateDto {
    @ApiModelProperty(name = "资源名称")
    @NotNull(message = "id不能为空")
    private Integer id;
    @ApiModelProperty(name = "资源名称")
    private String name;
    @ApiModelProperty(name = "父节点id")
    private Integer parentId;
    @ApiModelProperty(name = "图标")
    private String icon;
    @ApiModelProperty(name = "前端url")
    private String path;
    @ApiModelProperty(name = "排序")
    private Integer sort;
    @ApiModelProperty(name = "重定向路径")
    private String redirect;
    @ApiModelProperty(name = "是否有子菜单")
    private Boolean noDropdown;
    @ApiModelProperty(name = "类型")
    @NotNull(message = "类型不能为空")
    private Integer type;
    @ApiModelProperty(name = "后端接口")
    private String apis;
}
