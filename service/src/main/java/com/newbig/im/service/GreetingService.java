package com.newbig.im.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newbig.im.dal.config.DS;
import com.newbig.im.dal.config.DataSourceNames;
import com.newbig.im.dal.mapper.SysUserMapper;
import com.newbig.im.dal.model.SysUser;
import com.newbig.im.model.dto.RPCModel;
import com.newbig.im.init.RPCConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class GreetingService {
    @Autowired
    private SysUserMapper sysUserMapper;

    public String greet() {
        return "Hello World";
    }

    @DS(value = DataSourceNames.IMCONFIG_DS) //这个可以不加,默认就是
    public List<SysUser> getSysUserList() {
        return sysUserMapper.selectAll();
    }
}
