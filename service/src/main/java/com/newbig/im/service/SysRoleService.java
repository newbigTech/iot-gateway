package com.newbig.im.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newbig.im.common.utils.BeanCopyUtil;
import com.newbig.im.common.utils.StringUtil;
import com.newbig.im.dal.mapper.SysRoleMapper;
import com.newbig.im.dal.model.SysOrg;
import com.newbig.im.dal.model.SysRole;
import com.newbig.im.model.dto.SysRoleAddDto;
import com.newbig.im.model.dto.SysRoleUpdateDto;
import com.newbig.im.model.vo.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User: Haibo
 * Date: 2018-07-01 11:29:23
 * Desc:
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysOrgService sysOrgService;
    /**
     * 分页获取 sysRole 列表
     * @param roleName
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<SysRoleVo> getList(String roleName, Date gmtBegin, Date gmtEnd,
                                               int pageSize, int pageNum){
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",0);
        if(StringUtil.isNotBlank(roleName)){
            criteria.andLike("roleName",StringUtil.concat("%",roleName,"%"));
        }
        if(gmtBegin != null){
            criteria.andGreaterThanOrEqualTo("gmtModify",gmtBegin);
        }
        if(gmtEnd != null){
            criteria.andLessThanOrEqualTo("gmtModify",gmtEnd);
        }
        List<SysRole> list = sysRoleMapper.selectByExample(example);
        PageSerializable ps = new PageSerializable<>(list);

        List<Long> orgIds = list.stream().map(SysRole::getOrgId).collect(Collectors.toList());
        List<SysOrg> orgs = sysOrgService.getListByOrgIds(orgIds);
        Map<Long,String> mls = Maps.newHashMap();
        for(SysOrg sysOrg: orgs){
            mls.put(sysOrg.getId(),sysOrg.getName());
        }
        List<SysRoleVo> srv = Lists.newArrayList();
        for(SysRole sysRole: list){
            SysRoleVo sysRoleVo = new SysRoleVo();
            BeanCopyUtil.copyProperties(sysRole,sysRoleVo);
            sysRoleVo.setOrgName(mls.get(sysRole.getOrgId()));
            srv.add(sysRoleVo);
        }
        ps.setList(srv);
        return ps;
    }

    /**
    *获取详情
    * @param id
    * @return
    */
    public SysRole getDetailById(Long id){
        Example example = new Example(SysRole.class);
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    /**
    * 添加
    * @param sysRoleAddDto
    */
    public void addSysRole(SysRoleAddDto sysRoleAddDto){
        SysRole sysRole = new SysRole();
        BeanCopyUtil.copyProperties(sysRoleAddDto,sysRole);
        sysRoleMapper.insertSelective(sysRole);
    }
    /**
    * 更新
    * @param sysRoleUpdateDto
    */
    public void updateSysRole(SysRoleUpdateDto sysRoleUpdateDto){
        SysRole sysRole = new SysRole();
        BeanCopyUtil.copyProperties(sysRoleUpdateDto,sysRole);

        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted",0);
        criteria.andEqualTo("id",sysRoleUpdateDto.getId());
        sysRoleMapper.updateByExample(sysRole,example);
    }

    /**
    * 逻辑删除
    * @param id
    */
    public void deleteSysRole(Long id){
        SysRole sysRole = new SysRole();
        sysRole.setIsDeleted(true);
        sysRole.setId(id);
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }
}
