package com.newbig.im.dal.model;

import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "sys_resource")
public class SysResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 前端uri
     */
    private String uri;

    /**
     * 前端图标
     */
    private String icon;

    /**
     * 菜单级别
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 根节点id
     */
    @Column(name = "ancesstor_id")
    private Long ancesstorId;

    /**
     * 父节点id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 类型 菜单 or按钮
     */
    private String type;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_modify")
    private Date gmtModify;

    private String creator;

    private String modifier;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**
     * 菜单对应的后端接口
     */
    private String apis;
    @Transient
    private List<SysResource> children = Lists.newArrayList();
    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取资源名称
     *
     * @return name - 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name 资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取前端uri
     *
     * @return uri - 前端uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * 设置前端uri
     *
     * @param uri 前端uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 获取前端图标
     *
     * @return icon - 前端图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置前端图标
     *
     * @param icon 前端图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取菜单级别
     *
     * @return level - 菜单级别
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置菜单级别
     *
     * @param level 菜单级别
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取根节点id
     *
     * @return ancesstor_id - 根节点id
     */
    public Long getAncesstorId() {
        return ancesstorId;
    }

    /**
     * 设置根节点id
     *
     * @param ancesstorId 根节点id
     */
    public void setAncesstorId(Long ancesstorId) {
        this.ancesstorId = ancesstorId;
    }

    /**
     * 获取父节点id
     *
     * @return parent_id - 父节点id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父节点id
     *
     * @param parentId 父节点id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取类型 菜单 or按钮
     *
     * @return type - 类型 菜单 or按钮
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型 菜单 or按钮
     *
     * @param type 类型 菜单 or按钮
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return gmt_create
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * @return gmt_modify
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * @param gmtModify
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * @param modifier
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * @return is_deleted
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 获取菜单对应的后端接口
     *
     * @return apis - 菜单对应的后端接口
     */
    public String getApis() {
        return apis;
    }

    /**
     * 设置菜单对应的后端接口
     *
     * @param apis 菜单对应的后端接口
     */
    public void setApis(String apis) {
        this.apis = apis;
    }

    public List<SysResource> getChildren() {
        return children;
    }

    public void setChildren(List<SysResource> children) {
        this.children = children;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}