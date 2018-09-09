package com.newbig.im.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * User: Haibo
 * Date: 2018-01-22 20:48:10
 * Desc:
 */
@Setter
@Getter
@ToString
public class UserDeleteDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;

}
