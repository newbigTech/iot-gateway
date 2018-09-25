package com.newbig.im.app;

import com.newbig.im.app.config.ConsulConfigInitializer;
import com.newbig.im.core.consul.ConsulManager;
import com.newbig.im.init.SpringContext;
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
        SpringApplication sa = new SpringApplication(AppApplication.class);
        sa.addInitializers(new ConsulConfigInitializer());
        SpringContext.context = sa.run(args);
        ConsulManager.watchSharding();

    }
}
