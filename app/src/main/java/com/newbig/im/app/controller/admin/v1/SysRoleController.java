package com.newbig.im.app.controller.admin.v1;

import com.github.pagehelper.PageSerializable;
import com.newbig.im.common.constant.AppConstant;
import com.newbig.im.dal.model.SysRole;
import com.newbig.im.model.dto.SysRoleAddDto;
import com.newbig.im.model.dto.SysRoleDeleteDto;
import com.newbig.im.model.dto.SysRoleUpdateDto;
import com.newbig.im.model.vo.ResponseVo;
import com.newbig.im.model.vo.SysRoleVo;
import com.newbig.im.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * User: Haibo
 * Date: 2018-07-01 11:29:23
 * Desc:
 */
@RestController
@Slf4j
@RequestMapping(value = AppConstant.API_PREFIX_V1+"/sysRole")
@Api(value = "sysRole相关api.", tags = {"sysRole"})
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;

    @ApiOperation(value = "获取列表")
    @GetMapping(value = "/list")
    public ResponseVo<PageSerializable<SysRoleVo>> getList(
        @RequestParam(required = false) String roleName,
        @RequestParam(required = false) Date gmtBegin,
        @RequestParam(required = false) Date gmtEnd,
        @RequestParam(required = false,defaultValue = "1") int pageNum,
        @RequestParam(required = false,defaultValue = "20") int pageSize
    ){
        return ResponseVo.success(sysRoleService.getList(roleName,gmtBegin,gmtEnd,pageSize,pageNum));
    }

    @ApiOperation(value = "获取详情")
    @GetMapping(value = "/get")
    public ResponseVo<SysRole> getDetail(
        @RequestParam(required = false) Long id ){
        return ResponseVo.success(sysRoleService.getDetailById(id));
    }

    @ApiOperation(value = "增加")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody SysRoleAddDto sysRoleAddDto){
        sysRoleService.addSysRole(sysRoleAddDto);
        return ResponseVo.success("保存成功");
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public ResponseVo update(@Valid @RequestBody SysRoleUpdateDto sysRoleUpdateDto){
        sysRoleService.updateSysRole(sysRoleUpdateDto);
        return ResponseVo.success("更新成功");
    }


    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ResponseVo delete(@Valid @RequestBody SysRoleDeleteDto sysRoleDeleteDto){
        sysRoleService.deleteSysRole(sysRoleDeleteDto.getId());
        return ResponseVo.success("删除成功");
    }
}
