package com.newbig.im.app.controller.admin.v1;

import com.github.pagehelper.PageSerializable;
import com.newbig.im.common.constant.AppConstant;
import com.newbig.im.dal.model.SysUserRole;
import com.newbig.im.model.dto.SysUserRoleAddDto;
import com.newbig.im.model.dto.SysUserRoleDeleteDto;
import com.newbig.im.model.dto.SysUserRoleUpdateDto;
import com.newbig.im.model.vo.ResponseVo;
import com.newbig.im.service.SysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * User: Haibo
 * Date: 2018-05-01 10:01:11
 * Desc:
 */
@RestController
@Slf4j
@RequestMapping(value = AppConstant.API_PREFIX_V1+"/sysUserRole")
@Api(value = "sysUserRole相关api.")
public class SysUserRoleController {
    @Resource
    private SysUserRoleService sysUserRoleService;

    @ApiOperation(value = "获取列表")
    @GetMapping(value = "/list")
    public ResponseVo<PageSerializable<SysUserRole>> getList(
        @RequestParam(required = false,defaultValue = "1") int pageNum,
        @RequestParam(required = false,defaultValue = "20") int pageSize
    ){
        return ResponseVo.success(sysUserRoleService.getList(pageSize,pageNum));
    }

    @ApiOperation(value = "获取详情")
    @GetMapping(value = "/get")
    public ResponseVo<SysUserRole> getDetail(
        @RequestParam(required = false) Integer id ){
        return ResponseVo.success(sysUserRoleService.getDetailById(id));
    }

    @ApiOperation(value = "增加")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody SysUserRoleAddDto sysUserRoleAddDto){
        sysUserRoleService.addSysUserRole(sysUserRoleAddDto);
        return ResponseVo.success("保存成功");
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public ResponseVo update(@Valid @RequestBody SysUserRoleUpdateDto sysUserRoleUpdateDto){
        sysUserRoleService.updateSysUserRole(sysUserRoleUpdateDto);
        return ResponseVo.success("更新成功");
    }


    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ResponseVo delete(@Valid @RequestBody SysUserRoleDeleteDto sysUserRoleDeleteDto){
        sysUserRoleService.deleteSysUserRole(sysUserRoleDeleteDto.getId());
        return ResponseVo.success("删除成功");
    }
}
