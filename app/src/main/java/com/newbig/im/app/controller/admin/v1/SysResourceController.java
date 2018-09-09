package com.newbig.im.app.controller.admin.v1;

import com.github.pagehelper.PageSerializable;
import com.newbig.im.common.constant.AppConstant;
import com.newbig.im.dal.model.SysResource;
import com.newbig.im.model.dto.SysResourceAddDto;
import com.newbig.im.model.dto.SysResourceDeleteDto;
import com.newbig.im.model.dto.SysResourceUpdateDto;
import com.newbig.im.model.vo.ResponseVo;
import com.newbig.im.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * User: Haibo
 * Date: 2018-05-01 10:03:14
 * Desc:
 */
@RestController
@Slf4j
@RequestMapping(value = AppConstant.API_PREFIX_V1+"/sysResource")
@Api(value = "sysResource相关api.")
public class SysResourceController {
    @Resource
    private SysResourceService sysResourceService;

    @ApiOperation(value = "获取列表")
    @GetMapping(value = "/list")
    public ResponseVo<PageSerializable<SysResource>> getList(
        @RequestParam(required = false,defaultValue = "1") int pageNum,
        @RequestParam(required = false,defaultValue = "20") int pageSize
    ){
        return ResponseVo.success(sysResourceService.getList(pageSize,pageNum));
    }

    @ApiOperation(value = "获取详情")
    @GetMapping(value = "/get")
    public ResponseVo<SysResource> getDetail(
        @RequestParam(required = false) Long id ){
        return ResponseVo.success(sysResourceService.getDetailById(id));
    }

    @ApiOperation(value = "增加")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody SysResourceAddDto sysResourceAddDto){
        sysResourceService.addSysResource(sysResourceAddDto);
        return ResponseVo.success("保存成功");
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public ResponseVo update(@Valid @RequestBody SysResourceUpdateDto sysResourceUpdateDto){
        sysResourceService.updateSysResource(sysResourceUpdateDto);
        return ResponseVo.success("更新成功");
    }


    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ResponseVo delete(@Valid @RequestBody SysResourceDeleteDto sysResourceDeleteDto){
        sysResourceService.deleteSysResource(sysResourceDeleteDto.getId());
        return ResponseVo.success("删除成功");
    }
}
