package com.adtec.rdc.base.gen.mapper;

import com.adtec.rdc.base.gen.model.po.TableInfo;
import com.adtec.rdc.base.gen.model.query.TableInfoQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import org.apache.ibatis.annotations.Param;

/**
 * @author: JTao
 * @date: 2018/11/8 09:59
 */

public interface TableInfoMapper extends BaseMapper<TableInfo> {

    /**
     * 分页查询表信息
     * @param tableInfoQuery
     * @return
     */
    IPage<TableInfo> pageByQuery(TableInfoQuery tableInfoQuery);


    /**
     * 查询单个表信息
     * @param tableName
     * @return
     */
    TableInfo getOne(@Param("tableName") String tableName);

}
