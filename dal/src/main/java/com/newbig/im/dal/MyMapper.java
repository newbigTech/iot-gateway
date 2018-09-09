package com.newbig.im.dal;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * User: haibo
 * Date: 2018/3/19 下午6:35
 * Desc:
 */
public interface MyMapper<T> extends Mapper<T>, ConditionMapper<T>, IdsMapper<T>, InsertListMapper<T> {
}
