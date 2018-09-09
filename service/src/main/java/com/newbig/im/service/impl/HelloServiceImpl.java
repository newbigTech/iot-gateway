package com.newbig.im.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newbig.im.model.dto.RPCModel;
import com.newbig.im.service.HelloService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String string) {
        System.out.println("Server receive: " + string);
        return "hello " + string + " ！";
    }
    @Override
    public RPCModel sayModel(RPCModel model) {
        log.info(JSON.toJSONString(model));
        RPCModel mode = new RPCModel();
        mode.setOne("1111");
        mode.setTwo(2222);
        mode.setThree(3333L);
        mode.setFour(new BigDecimal("12.0"));
        mode.setFive(Lists.newArrayList("1233de"));
        Map<String,Integer> six = Maps.newHashMap();
        six.put("aaasd",3434);
        mode.setSix(six);
        return mode;
    }
}