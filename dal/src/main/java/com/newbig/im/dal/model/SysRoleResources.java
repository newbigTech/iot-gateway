package com.newbig.im.dal.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "sys_role_resources")
public class SysRoleResources {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 所属组织id
     */
    @Column(name = "org_id")
    private String orgId;

    @Column(name = "gmt_create")
    private Date gmtCreate;

    @Column(name = "gmt_modify")
    private Date gmtModify;

    private String creator;

    private String modifier;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "role_name")
    private String roleName;

    /**
     * 有权限的资源id
     */
    @Column(name = "resource_ids")
    private String resourceIds;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取所属组织id
     *
     * @return org_id - 所属组织id
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 设置所属组织id
     *
     * @param orgId 所属组织id
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
     * @return role_name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取有权限的资源id
     *
     * @return resource_ids - 有权限的资源id
     */
    public String getResourceIds() {
        return resourceIds;
    }

    /**
     * 设置有权限的资源id
     *
     * @param resourceIds 有权限的资源id
     */
    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}