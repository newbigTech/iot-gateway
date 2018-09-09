package com.newbig.im.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.google.common.collect.Lists;
import com.newbig.im.common.exception.UserRemindException;
import com.newbig.im.common.utils.StringUtil;
import com.newbig.im.dal.mapper.SysOrgMapper;
import com.newbig.im.dal.model.SysOrg;
import com.newbig.im.model.dto.SysOrgAddDto;
import com.newbig.im.model.dto.SysOrgUpdateDto;
import com.newbig.im.service.helper.OrgHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User: Haibo
 * Date: 2018-06-03 15:50:00
 * Desc:
 */
@Service
public class SysOrgService {

    @Autowired
    private SysOrgMapper sysOrgMapper;

    /**
     * 分页获取 sysOrg 列表
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<SysOrg> getTree(int pageSize, int pageNum){
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(SysOrg.class);
        example.createCriteria()
                .andEqualTo("isDeleted",0)
                .andEqualTo("level",0);
        List<SysOrg> list = sysOrgMapper.selectByExample(example);
        PageSerializable ps = new PageSerializable(list);
        if(CollectionUtils.isEmpty(list)){
            return ps;
        }
        List<Long> orgIds = list.stream().map(SysOrg::getId).collect(Collectors.toList());
        example.clear();
        example.createCriteria()
                .andEqualTo("isDeleted",0)
                .andIn("ancesstorId",orgIds);
        List<SysOrg> orgs = sysOrgMapper.selectByExample(example);
        List<SysOrg> orgTrees = OrgHelper.buildOrgTree(orgs);
        ps.setList(orgTrees);
        return ps;
    }
    /**
     * 分页获取 sysOrg 列表
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<SysOrg> getList(String name,int pageSize, int pageNum){
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(SysOrg.class);
        Example.Criteria criteria =example.createCriteria();
        criteria.andEqualTo("isDeleted",0);
        if(StringUtil.isNotBlank(name)){
            criteria.andLike("name",StringUtil.concat("%",name,"%"));
        }
        example.setOrderByClause("level ASC");
        List<SysOrg> list = sysOrgMapper.selectByExample(example);
        return new PageSerializable<>(list);
    }

    /**
    *获取详情
    * @param id
    * @return
    */
    public SysOrg getDetailById(Long id){
        return sysOrgMapper.selectByPrimaryKey(id);
    }

    /**
    * 添加
    * @param sysOrgAddDto
    */
    public void addSysOrg(SysOrgAddDto sysOrgAddDto){
        SysOrg sysOrg = new SysOrg();
        buildSysOrg(sysOrg,sysOrgAddDto.getParentId(),sysOrgAddDto.getName());
        sysOrgMapper.insertSelective(sysOrg);
    }
    /**
    * 更新
    * @param sysOrgUpdateDto
    */
    public void updateSysOrg(SysOrgUpdateDto sysOrgUpdateDto){
        SysOrg sysOrg = new SysOrg();
        sysOrg.setId(sysOrgUpdateDto.getId());
        buildSysOrg(sysOrg,sysOrgUpdateDto.getParentId(),sysOrgUpdateDto.getName());
        sysOrgMapper.updateByPrimaryKeySelective(sysOrg);
    }

    private void buildSysOrg(SysOrg sysOrg,Long parentId,String name){
        sysOrg.setName(name);
        if(null != parentId){
            SysOrg parentOrg = getDetailById(parentId);
            if(null == parentOrg){
                throw new UserRemindException("父节点不存在");
            }
            sysOrg.setParentId(parentOrg.getId());
            sysOrg.setAncesstorId(parentOrg.getAncesstorId());
            sysOrg.setParentIds(StringUtil.concat(parentOrg.getLevel() == 0?"":parentOrg.getParentIds(),parentOrg.getId(),","));
            sysOrg.setLevel(parentOrg.getLevel()+1);
        }else{
            sysOrg.setParentId(1L);
            sysOrg.setAncesstorId(1);
            sysOrg.setParentIds("1,");
            sysOrg.setLevel(1);
        }
    }
    /**
    * 逻辑删除
    * @param id
    */
    public void deleteSysOrg(Long id){
        SysOrg sysOrg = new SysOrg();
        sysOrg.setIsDeleted(true);
        sysOrg.setId(id);
        sysOrgMapper.updateByPrimaryKeySelective(sysOrg);
    }

    /**
     * 根据主键id查询
     * @param orgIds
     * @return
     */
    public List<SysOrg> getListByOrgIds(List<Long> orgIds) {
        if(CollectionUtils.isEmpty(orgIds)){
            return Lists.newArrayList();
        }
        Example example = new Example(SysOrg.class);
        example.createCriteria()
                .andEqualTo("isDeleted",0)
                .andIn("id",orgIds);
        return sysOrgMapper.selectByExample(example);
    }
}
