package com.newbig.im.app.controller.admin.v1;

import com.newbig.im.common.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: haibo
 * Date: 2018/1/17 下午1:49
 * Desc: 我的 只能查看 修改自己的信息
 */
@RestController
@Slf4j
@RequestMapping(value = AppConstant.API_PREFIX_V1+"/mine")
public class MineController {
//    @Autowired
//    private UserService userService;
//    @ApiOperation(value = "获取个人详情")
//    @GetMapping(value = "/detail")
//    public ResponseVo<UserDetail> getUser(HttpServletRequest request){
//        String userId = JwtUtil.getUserUuid(request);
//        UserDetail userDetail = userService.getUserDetailByUserId(userId);
//        return ResponseVo.success(userDetail);
//    }
}
