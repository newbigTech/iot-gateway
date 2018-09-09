package com.newbig.im.model.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * User: Haibo
 * Date: 2018-06-17 15:50:00
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysOrgTree {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("parentId")
    private Integer parentId;
    /**
     * 所有下级组织
     */
    private List<SysOrgTree> children = Lists.newArrayList();
    /**
     * 所有上级组织
     */
    private List<SysOrgTree> parents = Lists.newArrayList();
}
