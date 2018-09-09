package com.newbig.im.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.newbig.im.common.utils.BeanCopyUtil;
import com.newbig.im.dal.mapper.SysUserRoleMapper;
import com.newbig.im.dal.model.SysUserRole;
import com.newbig.im.model.dto.SysUserRoleAddDto;
import com.newbig.im.model.dto.SysUserRoleUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * User: Haibo
 * Date: 2018-05-01 10:01:11
 * Desc:
 */
@Service
public class SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 分页获取 sysUserRole 列表
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<SysUserRole> getList(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", 0);
        List<SysUserRole> list = sysUserRoleMapper.selectByExample(example);
        return new PageSerializable<>(list);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    public SysUserRole getDetailById(Integer id) {
        Example example = new Example(SysUserRole.class);
        return sysUserRoleMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加
     *
     * @param sysUserRoleAddDto
     */
    public void addSysUserRole(SysUserRoleAddDto sysUserRoleAddDto) {
        SysUserRole sysUserRole = new SysUserRole();
        BeanCopyUtil.copyProperties(sysUserRoleAddDto, sysUserRole);
        sysUserRoleMapper.insertSelective(sysUserRole);
    }

    /**
     * 更新
     *
     * @param sysUserRoleUpdateDto
     */
    public void updateSysUserRole(SysUserRoleUpdateDto sysUserRoleUpdateDto) {
        SysUserRole sysUserRole = new SysUserRole();
        BeanCopyUtil.copyProperties(sysUserRoleUpdateDto, sysUserRole);

        Example example = new Example(SysUserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", 0);
        criteria.andEqualTo("id", sysUserRoleUpdateDto.getId());
        sysUserRoleMapper.updateByExample(sysUserRole, example);
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    public void deleteSysUserRole(Integer id) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setIsDeleted(true);
        sysUserRole.setId(id);
        sysUserRoleMapper.updateByPrimaryKeySelective(sysUserRole);
    }
}
