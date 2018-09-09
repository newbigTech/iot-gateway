package com.newbig.im.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@ApiModel(value = "资源树")
public class ResourceTreeVo {
    @ApiModelProperty(name = "父节点")
    public Integer parentId;
    @ApiModelProperty(name = "id")
    private Integer id;
    @ApiModelProperty(name = "资源名称")
    private String name;
    @ApiModelProperty(name = "路径")
    private String path;
    @ApiModelProperty(name = "图标")
    private String icon;
    @ApiModelProperty(name = "排序")
    private Integer sort;
    @ApiModelProperty(name = "权限控制用")
    private String spm;
    @ApiModelProperty(name = "类型")
    private Integer type;
    @ApiModelProperty(name = "第几级")
    private Integer level;
    @ApiModelProperty(name = "后端接口")
    private String apis;
    @ApiModelProperty(name = "重定向路径")
    private String redirect;
    @ApiModelProperty(name = "是否有子菜单")
    private Boolean noDropdown;
    @ApiModelProperty(name = "子节点")
    private List<ResourceTreeVo> children;
}
