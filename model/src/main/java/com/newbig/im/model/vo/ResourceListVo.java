package com.newbig.im.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@ApiModel(value = "资源列表")
public class ResourceListVo {
    @ApiModelProperty(name = "id")
    private Integer id;
    @ApiModelProperty(name = "资源名称")
    private String name;
    @ApiModelProperty(name = "级别")
    private Integer level;
    @ApiModelProperty(name = "图标")
    private String icon;
    @ApiModelProperty(name = "排序")
    private Integer sort;
    @ApiModelProperty(name = "重定向url")
    private String redirect;
    @ApiModelProperty(name = "类型 菜单 按钮 接口")
    private Integer type;
    @ApiModelProperty(name = "权限控制用")
    private String spm;
}
