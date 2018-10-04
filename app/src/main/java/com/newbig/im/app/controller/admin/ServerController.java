package com.newbig.im.app.controller.admin;

import com.newbig.im.common.annotation.NoAuth;
import com.newbig.im.common.utils.StringUtil;
import com.newbig.im.dal.model.SysUser;
import com.newbig.im.init.RPCConfig;
import com.newbig.im.init.ServerContext;
import com.newbig.im.model.vo.ResponseVo;
import com.newbig.im.service.GreetingService;
import com.newbig.im.service.OrderService;
import io.netty.util.internal.ThreadLocalRandom;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class ServerController {
    @Value("${server.tcp.port}")
    private String chatTcpPort;
    @Value("${server.websocket.port}")
    private String chatWebsocketPort;
    @Autowired
    private GreetingService greetingService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/getOnlineServers")
    @NoAuth
    @ApiOperation(value = "客户端获取可用的server地址")
    public ResponseVo<String> getOnlineServers(@RequestParam(required = true)String type) {
        List<String> onlineServers = ServerContext.onlineServers;
        if(CollectionUtils.isEmpty(onlineServers)){
            return ResponseVo.failure("no available server");
        }
        Integer random = ThreadLocalRandom.current()
                .nextInt(0,onlineServers.size());
        String host = ServerContext.onlineServers.get(random);
        if(Objects.equals("tcp",type)){
            host = StringUtil.concat(host,":",chatTcpPort);
        }else {
            host = StringUtil.concat(host,":",chatWebsocketPort);
        }
        return ResponseVo.success(host);
    }

    @GetMapping("/greeting")
    public String greeting() {
        return greetingService.greet();
    }
    @GetMapping("/userList")
    @NoAuth
    public List<SysUser> userList() {
        return greetingService.getSysUserList();
    }
    @GetMapping("/testSj")
    @NoAuth
    public String testSj() {
//         orderService.insertOrder();
        RPCConfig.getOnlineServer();
         return "ok";
    }
}
