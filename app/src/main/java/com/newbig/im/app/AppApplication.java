package com.newbig.im.app;

import com.newbig.im.app.config.ConsulConfigInitializer;
import com.newbig.im.init.ServerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import static com.newbig.im.common.constant.AppConstant.PACKAGE_NAME;

@Slf4j
@SpringBootApplication(scanBasePackages = PACKAGE_NAME)
@MapperScan(basePackages = PACKAGE_NAME+".dal.mapper")
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication sa = new SpringApplication(AppApplication.class);
        sa.addInitializers(new ConsulConfigInitializer());
        ServerContext.context = sa.run(args);
    }


}
