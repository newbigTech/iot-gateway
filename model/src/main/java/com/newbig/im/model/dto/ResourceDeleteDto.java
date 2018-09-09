package com.newbig.im.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * User: haibo
 * Date: 2017/10/2 下午6:16
 * Desc:
 */
@Setter
@Getter
@ToString
public class ResourceDeleteDto {
    @NotNull(message = "ids不能为空")
    @Size(min = 1, message = "参数不能为空")
    private List<Long> ids;

}
