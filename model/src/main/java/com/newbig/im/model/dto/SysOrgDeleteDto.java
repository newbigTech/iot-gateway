package com.newbig.im.model.dto;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User: Haibo
 * Date: 2018-06-03 15:50:00
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysOrgDeleteDto {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

}
