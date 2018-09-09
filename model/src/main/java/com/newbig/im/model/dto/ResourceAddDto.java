package com.newbig.im.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * User: haibo
 * Date: 2017/10/2 下午6:16
 * Desc:
 */
@Setter
@Getter
@ToString
public class ResourceAddDto {
    @ApiModelProperty(name = "资源名称")
    @NotEmpty(message = "资源名称不能为空")
    private String name;
    @ApiModelProperty(name = "父节点id")
//    @NotNull(message = "父节点id不能为空")
    private Integer parentId;
    @ApiModelProperty(name = "图标")
    @NotEmpty(message = "图标不能为空")
    private String icon;
    @ApiModelProperty(name = "前端url")
    @NotEmpty(message = "前端url不能为空")
    private String path;
    @ApiModelProperty(name = "排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;
    @ApiModelProperty(name = "重定向路径")
    @NotEmpty(message = "重定向路径不能为空")
    private String redirect;
    @ApiModelProperty(name = "spm")
    @NotEmpty(message = "spm不能为空")
    private String spm;
    //    @ApiModelProperty(name = "是否有子菜单")
//    @NotNull(message = "是否有子菜单不能为空")
//    private Boolean noDropdown;
//    @ApiModelProperty(name = "组件")
//    @NotEmpty(message = "组件不能为空")
//    private String component;
    @ApiModelProperty(name = "类型")
    @NotNull(message = "类型不能为空")
    private Integer type;
    @ApiModelProperty(name = "后端接口")
    @NotEmpty(message = "后端接口不能为空")
    private String apis;

}
