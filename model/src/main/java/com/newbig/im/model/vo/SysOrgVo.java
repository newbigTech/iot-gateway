package com.newbig.im.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
/**
 * User: Haibo
 * Date: 2018-06-03 15:50:00
 * Desc:
 */
@Setter
@Getter
@ToString
public class SysOrgVo {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("parentId")
    private Integer parentId;
    @ApiModelProperty("gmtCreate")
    private Date gmtCreate;
    @ApiModelProperty("gmtModify")
    private Date gmtModify;
    @ApiModelProperty("creator")
    private String creator;
    @ApiModelProperty("modifier")
    private String modifier;
    @ApiModelProperty("isDeleted")
    private Boolean isDeleted;
    @ApiModelProperty("ancesstorId")
    private Integer ancesstorId;
    @ApiModelProperty("level")
    private Integer level;
    @ApiModelProperty("parentIds")
    private String parentIds;
}
