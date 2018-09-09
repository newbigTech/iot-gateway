package com.newbig.im.app.controller.admin.v1;

import com.github.pagehelper.PageSerializable;
import com.newbig.im.common.constant.AppConstant;
import com.newbig.im.dal.model.SysDict;
import com.newbig.im.model.dto.SysDictAddDto;
import com.newbig.im.model.dto.SysDictDeleteDto;
import com.newbig.im.model.dto.SysDictUpdateDto;
import com.newbig.im.model.vo.ResponseVo;
import com.newbig.im.service.SysDictService;
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
@RequestMapping(value = AppConstant.API_PREFIX_V1+"/sysDict")
@Api(value = "sysDict相关api.")
public class SysDictController {
    @Resource
    private SysDictService sysDictService;

    @ApiOperation(value = "获取列表")
    @GetMapping(value = "/list")
    public ResponseVo<PageSerializable<SysDict>> getList(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String keyName,
        @RequestParam(required = false,defaultValue = "1") int pageNum,
        @RequestParam(required = false,defaultValue = "20") int pageSize
    ){
        return ResponseVo.success(sysDictService.getList(category,keyName,pageSize,pageNum));
    }

    @ApiOperation(value = "获取详情")
    @GetMapping(value = "/get")
    public ResponseVo<SysDict> getDetail(
        @RequestParam(required = false) Integer id ){
        return ResponseVo.success(sysDictService.getDetailById(id));
    }

    @ApiOperation(value = "增加")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody SysDictAddDto sysDictAddDto){
        sysDictService.addSysDict(sysDictAddDto);
        return ResponseVo.success("保存成功");
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "/update")
    public ResponseVo update(@Valid @RequestBody SysDictUpdateDto sysDictUpdateDto){
        sysDictService.updateSysDict(sysDictUpdateDto);
        return ResponseVo.success("更新成功");
    }


    @ApiOperation(value = "删除")
    @PostMapping(value = "/delete")
    public ResponseVo delete(@Valid @RequestBody SysDictDeleteDto sysDictDeleteDto){
        sysDictService.deleteSysDict(sysDictDeleteDto.getId());
        return ResponseVo.success("删除成功");
    }
}
