package com.newbig.im.dal.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_role")
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属组织id
     */
    @Column(name = "org_id")
    private Long orgId;

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
     * 有权限的org_id
     */
    @Column(name = "org_ids")
    private String orgIds;

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
     * 获取所属组织id
     *
     * @return org_id - 所属组织id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置所属组织id
     *
     * @param orgId 所属组织id
     */
    public void setOrgId(Long orgId) {
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

    /**
     * 获取有权限的org_id
     *
     * @return org_ids - 有权限的org_id
     */
    public String getOrgIds() {
        return orgIds;
    }

    /**
     * 设置有权限的org_id
     *
     * @param orgIds 有权限的org_id
     */
    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }
}