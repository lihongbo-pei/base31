package com.adtec.rdc.base.gen.model.config;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * @author: JTao
 * @date: 2018/11/8 13:50
 */
@Data
public class TableInfoConfig extends BaseConfig{

    /**
     * 表名
     */
    @TableId
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 类名
     */
    private String className;

    /**
     * 首字符小写的类名
     */
    private String lowerClassName;

    /**
     * 主键列
     */
    private ColumnInfoConfig pk;

    /**
     * 列信息
     */
    private List<ColumnInfoConfig> columnInfo;
}
