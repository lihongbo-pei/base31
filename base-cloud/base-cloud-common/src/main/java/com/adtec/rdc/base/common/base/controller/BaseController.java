package com.adtec.rdc.base.common.base.controller;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.common.util.ApiResult;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author: JTao
 * @date 2019-01-23 10:24
 */
public class BaseController<K extends BaseService<T>, T, P extends Serializable> {

    @Autowired
    private K baseService;

    @ApiOperation(value = "添加", httpMethod = "POST")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody T t){
        return new ApiResult<>(baseService.save(t));
    }

    @ApiOperation(value = "修改", httpMethod = "PUT")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody T t){
        return new ApiResult<>(baseService.updateById(t));
    }

    @ApiOperation(value = "删除", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") P id){
        return new ApiResult<>(baseService.removeById(id));
    }

    @ApiOperation(value = "主键查询", notes = "主键查询", httpMethod = "GET")
    @GetMapping("/{id}")
    public ApiResult<T> getById(@PathVariable("id") String id){
        return new ApiResult<>(baseService.getById(id));
    }

    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "GET")
    @GetMapping("/page")
    public ApiResult<IPage<T>> pageByQuery(IPage<T> t){
        return new ApiResult<>(baseService.pageByQuery(t));
    }

    @ApiOperation(value = "查询所有信息", notes = "查询所有信息", httpMethod = "GET")
    @GetMapping
    public ApiResult<Collection<T>> selectAll(){
        return new ApiResult<>(baseService.listByMap(new HashMap<>()));
    }

}
