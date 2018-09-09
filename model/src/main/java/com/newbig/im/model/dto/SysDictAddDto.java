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
 * Date: 2018-05-01 10:01:11
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysDictAddDto {
    @ApiModelProperty("category")
    @NotEmpty(message = "category不能为空")
    private String category;
    @ApiModelProperty("name")
    @NotEmpty(message = "keyName不能为空")
    private String keyName;
    @ApiModelProperty("value")
    @NotEmpty(message = "value不能为空")
    private String value;
    @ApiModelProperty("sort")
    @NotNull(message = "sort不能为空")
    private Integer sort;
}
