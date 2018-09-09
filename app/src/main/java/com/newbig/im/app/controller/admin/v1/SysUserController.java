package com.newbig.im.app.controller.admin.v1;

import com.github.pagehelper.PageSerializable;
import com.newbig.im.common.constant.AppConstant;
import com.newbig.im.dal.model.SysUser;
import com.newbig.im.model.dto.SysUserAddDto;
import com.newbig.im.model.dto.SysUserDeleteDto;
import com.newbig.im.model.dto.SysUserUpdateDto;
import com.newbig.im.model.vo.ResponseVo;
import com.newbig.im.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * User: Haibo
 * Date: 2018-05-01 10:05:30
 * Desc:
 */
@RestController
@Slf4j
@RequestMapping(value = AppConstant.API_PREFIX_V1+"/sysUser")
@Api(value = "sysUser相关api.")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    @ApiOperation(value = "获取列表")
    @GetMapping(value = "/list")
    public ResponseVo<PageSerializable<SysUser>> getList(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String mobile,
        @RequestParam(required = false,defaultValue = "1") int pageNum,
        @RequestParam(required = false,defaultValue = "20") int pageSize
    ){
        return ResponseVo.success(sysUserService.getList(name,mobile,pageSize,pageNum));
    }

    @ApiOperation(value = "获取详情")
    @GetMapping(value = "/get")
    public ResponseVo<SysUser> getDetail(
        @RequestParam(required = false) Integer id ){
        return ResponseVo.success(sysUserService.getDetailById(id));
    }

    @ApiOperation(value = "增加")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody SysUserAddDto sysUserAddDto){
        sysUserService.addSysUser(sysUserAddDto);
        return ResponseVo.success("保存成功");
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public ResponseVo update(@Valid @RequestBody SysUserUpdateDto sysUserUpdateDto){
        sysUserService.updateSysUser(sysUserUpdateDto);
        return ResponseVo.success("更新成功");
    }


    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ResponseVo delete(@Valid @RequestBody SysUserDeleteDto sysUserDeleteDto){
        sysUserService.deleteSysUser(sysUserDeleteDto.getId());
        return ResponseVo.success("删除成功");
    }
}
