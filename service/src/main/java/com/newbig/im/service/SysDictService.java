package com.newbig.im.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.newbig.im.common.utils.BeanCopyUtil;
import com.newbig.im.common.utils.StringUtil;
import com.newbig.im.dal.mapper.SysDictMapper;
import com.newbig.im.dal.model.SysDict;
import com.newbig.im.model.dto.SysDictAddDto;
import com.newbig.im.model.dto.SysDictUpdateDto;
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
public class SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    /**
     * 分页获取 sysDict 列表
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    public PageSerializable<SysDict> getList(String category,String keyName,int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(SysDict.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", 0);
        if(StringUtil.isNoneBlank(category)){
            criteria.andLike("category",StringUtil.concat("%",category,"%"));
        }
        if(StringUtil.isNoneBlank(keyName)){
            criteria.andLike("keyName",StringUtil.concat("%",keyName,"%"));
        }
        example.setOrderByClause("category DESC,sort ASC");
        List<SysDict> list = sysDictMapper.selectByExample(example);
        return new PageSerializable<>(list);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    public SysDict getDetailById(Integer id) {
        Example example = new Example(SysDict.class);
        return sysDictMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加
     *
     * @param sysDictAddDto
     */
    public void addSysDict(SysDictAddDto sysDictAddDto) {
        SysDict sysDict = new SysDict();
        BeanCopyUtil.copyProperties(sysDictAddDto, sysDict);
        sysDictMapper.insertSelective(sysDict);
    }

    /**
     * 更新
     *
     * @param sysDictUpdateDto
     */
    public void updateSysDict(SysDictUpdateDto sysDictUpdateDto) {
        SysDict sysDict = new SysDict();
        BeanCopyUtil.copyProperties(sysDictUpdateDto, sysDict);
        sysDict.setIsDeleted(null);
        sysDictMapper.updateByPrimaryKeySelective(sysDict);
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    public void deleteSysDict(Integer id) {
        SysDict sysDict = new SysDict();
        sysDict.setIsDeleted(true);
        sysDict.setId(id);
        sysDictMapper.updateByPrimaryKeySelective(sysDict);
    }
}
