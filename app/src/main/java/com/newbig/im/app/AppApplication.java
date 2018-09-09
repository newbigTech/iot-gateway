package com.newbig.im.app;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newbig.im.model.dto.RPCModel;
import com.newbig.im.service.HelloService;
import com.newbig.im.service.rpcconfig.RPCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

import java.math.BigDecimal;
import java.util.Map;

import static com.newbig.im.common.constant.AppConstant.PACKAGE_NAME;


@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = PACKAGE_NAME)
@MapperScan(basePackages = PACKAGE_NAME+".dal.mapper")
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
        RPCService.initRPC();
        log.info("-----------------------------");
        while (true) {
            HelloService helloService = RPCService.getHelloService("192.168.10.103");
            try {
                System.out.println(helloService.sayHello("world"));
                RPCModel mode = new RPCModel();
                mode.setOne("ffghfh");
                mode.setTwo(78);
                mode.setThree(376763L);
                mode.setFour(new BigDecimal("542.0"));
                mode.setFive(Lists.newArrayList("dfgfdgdf"));
                Map<String,Integer> six = Maps.newHashMap();
                six.put("aaasd",6565);
                mode.setSix(six);
                System.out.println(helloService.sayModel(mode).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
