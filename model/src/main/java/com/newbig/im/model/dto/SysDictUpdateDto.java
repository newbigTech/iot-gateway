package com.newbig.im.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class SysDictUpdateDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;
    @ApiModelProperty("category")
    private String category;
    @ApiModelProperty("keyName")
    private String keyName;
    @ApiModelProperty("value")
    private String value;
    @ApiModelProperty("sort")
    private Integer sort;

}
