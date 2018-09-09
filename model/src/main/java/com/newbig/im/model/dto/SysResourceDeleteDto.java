package com.newbig.im.model.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * User: Haibo
 * Date: 2018-05-01 10:03:14
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysResourceDeleteDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

}
