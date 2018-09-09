package com.newbig.im.app.config;

import com.newbig.im.common.utils.DateTimeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;
import static com.newbig.im.common.constant.AppConstant.PACKAGE_NAME;
import static com.newbig.im.common.constant.AppConstant.TOKEN_HEADER;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACKAGE_NAME))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(newArrayList(apiKey()))
//                .genericModelSubstitutes(ResponseVo.class)
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("APIs Doc")
                .description("接口文档" + DateTimeUtils.getNow() + " 更新")
                .termsOfServiceUrl("http://127.0.0.1:8888/")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(TOKEN_HEADER, TOKEN_HEADER, "header");
    }

}
