package com.newbig.im.app.controller.admin;

import com.newbig.im.app.AppApplication;
import com.newbig.im.common.utils.JwtUtil;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import redis.embedded.RedisServer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.newbig.im.common.constant.AppConstant.TOKEN_HEADER;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AppApplication.class, MockServletContext.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingControllerHttpRequestTest {

    @LocalServerPort
    private int port;
    private String baseUrl;

    @Autowired
    private TestRestTemplate restTemplate;
    HttpEntity<String> requestEntity;
    EmbeddedMysql mysqld;
    RedisServer redisServer;
    @Before
    public void setUp(){
        baseUrl = "http://localhost:" + port;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(TOKEN_HEADER, JwtUtil.genToken("12",12L));
        requestEntity = new HttpEntity<String>(null, requestHeaders);
        MysqldConfig config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(5306)
                .withUser("test", "123456")
                .withTimeZone("Asia/Shanghai")
                .withTimeout(2, TimeUnit.MINUTES)
                .withServerVariable("max_connect_errors", 666)
                .build();

        mysqld = anEmbeddedMysql(config)
                .addSchema("codetemplate", classPathScript("/sql/codetemplate.sql"))
                .start();
//        https://github.com/ozimov/embedded-redis
        redisServer = new RedisServer(8379);
        redisServer.start();
    }
    @Test
    public void greetingShouldReturnDefaultMessage() {
        assertThat(this.restTemplate
                .exchange(baseUrl+ "/greeting",
                        HttpMethod.GET,requestEntity,String.class
                ).getBody().contains("Hello World"));
    }
    @Test
    public void testUserList() {
        assertThat(this.restTemplate
                .exchange(baseUrl+ "/userList",
                        HttpMethod.GET,requestEntity, List.class
                ).getBody().size() == 0);
    }
    @After
    public void destroy(){
        mysqld.stop();
        redisServer.stop();
    }
}