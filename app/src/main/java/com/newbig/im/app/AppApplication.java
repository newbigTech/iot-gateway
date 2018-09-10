package com.newbig.im.app;

import com.newbig.im.service.rpcconfig.RPCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import static com.newbig.im.common.constant.AppConstant.PACKAGE_NAME;


@SpringBootApplication(scanBasePackages = PACKAGE_NAME)
@Slf4j
@MapperScan(basePackages = PACKAGE_NAME+".dal.mapper")
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
        RPCService.initRPC();
    }
}
