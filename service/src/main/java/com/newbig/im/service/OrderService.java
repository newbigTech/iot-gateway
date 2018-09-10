package com.newbig.im.service;

import com.google.common.collect.Lists;
import com.newbig.im.dal.mapper.OrderMapper;
import com.newbig.im.dal.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderService {
    private static Long l = 0L;
    @Autowired
    private OrderMapper orderMapper;

    public void insertOrder(){
        for(int j=0;j<10;j++) {
            List<Order> lo = Lists.newArrayListWithExpectedSize(2);
            for (int i = 0; i < 2; i++) {
                l++;
                Order order = new Order();
                order.setAmount(10l);
                order.setUserId(ThreadLocalRandom.current().nextInt(0, 1000000000));
                order.setOrderId(ThreadLocalRandom.current().nextLong(0, 1000000000));
                order.setPayPrice(new BigDecimal("102.30"));
                order.setTotalPrice(new BigDecimal("200.09"));
                order.setStatus(1);
                order.setBuyerName("aaa");
                order.setBuyerPhone("11111111");
                System.out.println(order.getOrderId());
                lo.add(order);
            }
            orderMapper.insertList(lo);
        }
    }
}
