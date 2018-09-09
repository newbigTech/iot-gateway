package com.newbig.im.app.controller.admin.v1;

import com.github.pagehelper.PageSerializable;
import com.newbig.im.common.constant.AppConstant;
import com.newbig.im.dal.model.SysOrg;
import com.newbig.im.model.dto.SysOrgAddDto;
import com.newbig.im.model.dto.SysOrgDeleteDto;
import com.newbig.im.model.dto.SysOrgUpdateDto;
import com.newbig.im.model.vo.ResponseVo;
import com.newbig.im.service.SysOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * User: Haibo
 * Date: 2018-06-03 15:50:00
 * Desc:
 */
@RestController
@Slf4j
@RequestMapping(value = AppConstant.API_PREFIX_V1+"/sysOrg")
@Api(value = "sysOrg相关api.")
public class SysOrgController {
    @Resource
    private SysOrgService sysOrgService;

    @ApiOperation(value = "获取列表")
    @GetMapping(value = "/tree")
    public ResponseVo<PageSerializable<SysOrg>> getTree(
        @RequestParam(required = false,defaultValue = "1") int pageNum,
        @RequestParam(required = false,defaultValue = "20") int pageSize
    ){
        return ResponseVo.success(sysOrgService.getTree(pageSize,pageNum));
    }
    @ApiOperation(value = "获取列表")
    @GetMapping(value = "/list")
    public ResponseVo<PageSerializable<SysOrg>> getList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false,defaultValue = "1") int pageNum,
            @RequestParam(required = false,defaultValue = "20") int pageSize
    ){
        return ResponseVo.success(sysOrgService.getList(name,pageSize,pageNum));
    }
    @ApiOperation(value = "获取详情")
    @GetMapping(value = "/get")
    public ResponseVo<SysOrg> getDetail(
        @RequestParam(required = false) Long id ){
        return ResponseVo.success(sysOrgService.getDetailById(id));
    }

    @ApiOperation(value = "增加")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody SysOrgAddDto sysOrgAddDto){
        sysOrgService.addSysOrg(sysOrgAddDto);
        return ResponseVo.success("保存成功");
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public ResponseVo update(@Valid @RequestBody SysOrgUpdateDto sysOrgUpdateDto){
        sysOrgService.updateSysOrg(sysOrgUpdateDto);
        return ResponseVo.success("更新成功");
    }


    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ResponseVo delete(@Valid @RequestBody SysOrgDeleteDto sysOrgDeleteDto){
        sysOrgService.deleteSysOrg(sysOrgDeleteDto.getId());
        return ResponseVo.success("删除成功");
    }
}
