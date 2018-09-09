package com.newbig.im.dal.model;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "sys_org")
public class SysOrg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父节点
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modify")
    private Date gmtModify;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 是否已删除
     */
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    /**
     * 根节点
     */
    @Column(name = "ancesstor_id")
    private Integer ancesstorId;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 所有父节点
     */
    @Column(name = "parent_ids")
    private String parentIds;
    /**
     * 所有下级组织
     */
    @Transient
    private List<SysOrg> children = Lists.newArrayList();
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
     * 获取部门名称
     *
     * @return name - 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置部门名称
     *
     * @param name 部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取父节点
     *
     * @return parent_id - 父节点
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父节点
     *
     * @param parentId 父节点
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取修改时间
     *
     * @return gmt_modify - 修改时间
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * 设置修改时间
     *
     * @param gmtModify 修改时间
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取修改人
     *
     * @return modifier - 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人
     *
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取是否已删除
     *
     * @return is_deleted - 是否已删除
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置是否已删除
     *
     * @param isDeleted 是否已删除
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 获取根节点
     *
     * @return ancesstor_id - 根节点
     */
    public Integer getAncesstorId() {
        return ancesstorId;
    }

    /**
     * 设置根节点
     *
     * @param ancesstorId 根节点
     */
    public void setAncesstorId(Integer ancesstorId) {
        this.ancesstorId = ancesstorId;
    }

    /**
     * 获取级别
     *
     * @return level - 级别
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置级别
     *
     * @param level 级别
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取所有父节点
     *
     * @return parent_ids - 所有父节点
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 设置所有父节点
     *
     * @param parentIds 所有父节点
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public List<SysOrg> getChildren() {
        return children;
    }

    public void setChildren(List<SysOrg> children) {
        this.children = children;
    }
}