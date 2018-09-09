package com.newbig.im.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.newbig.im.common.exception.UserRemindException;
import com.newbig.im.common.utils.BeanCopyUtil;
import com.newbig.im.dal.mapper.SysResourceMapper;
import com.newbig.im.dal.model.SysResource;
import com.newbig.im.model.dto.SysResourceAddDto;
import com.newbig.im.model.dto.SysResourceUpdateDto;
import com.newbig.im.service.helper.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * User: Haibo
 * Date: 2018-05-01 10:03:14
 * Desc:
 */
@Service
public class SysResourceService {

    @Autowired
    private SysResourceMapper sysResourceMapper;

    /**
     * 分页获取 sysResource 列表
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<SysResource> getList(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(SysResource.class);
        example.createCriteria()
                .andEqualTo("isDeleted",0)
                .andEqualTo("level",0);
        List<SysResource> list = sysResourceMapper.selectByExample(example);
        PageSerializable ps = new PageSerializable(list);
        if(CollectionUtils.isEmpty(list)){
            return ps;
        }
        List<Long> orgIds = list.stream().map(SysResource::getId).collect(Collectors.toList());
        orgIds.add(0L);
        example.clear();
        example.createCriteria()
                .andEqualTo("isDeleted",0)
                .andIn("ancesstorId",orgIds);
        List<SysResource> orgs = sysResourceMapper.selectByExample(example);
        List<SysResource> orgTrees = ResourceHelper.buildResourceTreeVo(orgs);
        ps.setList(orgTrees);
        return ps;
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    public SysResource getDetailById(Long id) {
        Example example = new Example(SysResource.class);
        return sysResourceMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加
     *
     * @param sysResourceAddDto
     */
    public void addSysResource(SysResourceAddDto sysResourceAddDto) {
        SysResource sysResource = new SysResource();
        BeanCopyUtil.copyProperties(sysResourceAddDto, sysResource);
        if(null != sysResourceAddDto.getParentId()
                && 0 != sysResourceAddDto.getParentId()){
            SysResource parent = getDetailById(sysResourceAddDto.getParentId());
            if(null == parent){
                throw new UserRemindException("父节点不存在");
            }
            sysResource.setParentId(parent.getId());
            sysResource.setAncesstorId(parent.getAncesstorId());
            sysResource.setLevel(parent.getLevel()+1);
        }else{
            sysResource.setParentId(0L);
            sysResource.setAncesstorId(0L);
            sysResource.setLevel(0);
        }
        sysResourceMapper.insertSelective(sysResource);
    }

    /**
     * 更新
     *
     * @param sysResourceUpdateDto
     */
    public void updateSysResource(SysResourceUpdateDto sysResourceUpdateDto) {
        SysResource sysResource = new SysResource();
        BeanCopyUtil.copyProperties(sysResourceUpdateDto, sysResource);

        if(null != sysResourceUpdateDto.getParentId()
                && 0 != sysResourceUpdateDto.getParentId()){
            SysResource parent = getDetailById(sysResourceUpdateDto.getParentId());
            if(null == parent){
                throw new UserRemindException("父节点不存在");
            }
            sysResource.setParentId(parent.getId());
            sysResource.setAncesstorId(parent.getAncesstorId());
            sysResource.setLevel(parent.getLevel()+1);
        }else{
            sysResource.setParentId(0L);
            sysResource.setAncesstorId(0L);
            sysResource.setLevel(0);
        }
        sysResourceMapper.updateByPrimaryKeySelective(sysResource);
    }
    /**
     * 逻辑删除
     *
     * @param id
     */
    public void deleteSysResource(Long id) {
        SysResource sysResource = new SysResource();
        sysResource.setIsDeleted(true);
        sysResource.setId(id);
        sysResourceMapper.updateByPrimaryKeySelective(sysResource);
    }
}
