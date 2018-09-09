package com.newbig.im.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.newbig.im.common.utils.BeanCopyUtil;
import com.newbig.im.common.utils.StringUtil;
import com.newbig.im.dal.mapper.SysUserMapper;
import com.newbig.im.dal.model.SysUser;
import com.newbig.im.model.dto.SysUserAddDto;
import com.newbig.im.model.dto.SysUserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * User: Haibo
 * Date: 2018-05-01 10:05:30
 * Desc:
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 分页获取 sysUser 列表
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<SysUser> getList(String name,String mobile,int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", 0);
        if(StringUtil.isNoneBlank(name)){
            criteria.andLike("name",StringUtil.concat("%",name,"%"));
        }
        if(StringUtil.isNoneBlank(mobile)){
            criteria.andLike("mobile",StringUtil.concat("%",mobile,"%"));
        }
        List<SysUser> list = sysUserMapper.selectByExample(example);
        return new PageSerializable<>(list);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    public SysUser getDetailById(Integer id) {
        Example example = new Example(SysUser.class);
        return sysUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加
     *
     * @param sysUserAddDto
     */
    public void addSysUser(SysUserAddDto sysUserAddDto) {
        SysUser sysUser = new SysUser();
        BeanCopyUtil.copyProperties(sysUserAddDto, sysUser);
        sysUserMapper.insertSelective(sysUser);
    }

    /**
     * 更新
     *
     * @param sysUserUpdateDto
     */
    public void updateSysUser(SysUserUpdateDto sysUserUpdateDto) {
        SysUser sysUser = new SysUser();
        BeanCopyUtil.copyProperties(sysUserUpdateDto, sysUser);

        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", 0);
        criteria.andEqualTo("id", sysUserUpdateDto.getId());
        sysUserMapper.updateByExample(sysUser, example);
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    public void deleteSysUser(Integer id) {
        SysUser sysUser = new SysUser();
        sysUser.setIsDeleted(true);
        sysUser.setId(id);
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    public SysUser getUserByMobile(String mobile) {
        SysUser sysUser = new SysUser();
        sysUser.setMobile(mobile);
        return sysUserMapper.selectOne(sysUser);
    }
}
