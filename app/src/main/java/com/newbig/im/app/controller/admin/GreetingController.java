package com.newbig.im.app.controller.admin;

import com.newbig.im.common.annotation.NoAuth;
import com.newbig.im.dal.model.SysUser;
import com.newbig.im.service.GreetingService;
import com.newbig.im.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GreetingController {
    @Autowired
    private GreetingService greetingService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/greeting")
    public String greeting() {
        return greetingService.greet();
    }
    @RequestMapping("/userList")
    public List<SysUser> userList() {
        return greetingService.getSysUserList();
    }
    @GetMapping("/testSj")
    @NoAuth
    public String testSj() {
         orderService.insertOrder();
         return "ok";
    }
}
