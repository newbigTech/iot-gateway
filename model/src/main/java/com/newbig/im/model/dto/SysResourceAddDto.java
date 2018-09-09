package com.newbig.im.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * User: Haibo
 * Date: 2018-05-01 10:03:14
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysResourceAddDto {
    @ApiModelProperty("name")
    @NotEmpty(message = "name不能为空")
    private String name;
    @ApiModelProperty("uri")
    @NotEmpty(message = "uri不能为空")
    private String uri;
    @ApiModelProperty("icon")
    @NotEmpty(message = "icon不能为空")
    private String icon;
    @ApiModelProperty("parentId")
    @NotNull(message = "parentId不能为空")
    private Long parentId;
    @ApiModelProperty("type")
    @NotEmpty(message = "type不能为空")
    private String type;
    @ApiModelProperty("apis")
    @NotEmpty(message = "apis不能为空")
    private String apis;

}
