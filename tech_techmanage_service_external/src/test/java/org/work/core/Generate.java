package org.work.core;

import com.work.ppm.commons.utils.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.work.constant.Constant;
import org.work.utils.JdbcUtil;

/**
 * 实体类
 */
@Slf4j
@Data
public class Generate {
    public Generate(String classNameUpperAll) {
        this.tableName = classNameUpperAll.toUpperCase();
        this.classNameUpperAll = StringUtil.processClassName(classNameUpperAll.toUpperCase());
        this.classNameUpper = StringUtil.processClassName(classNameUpperAll.toLowerCase());
        this.classNameLower = StringUtil.lowerName(this.classNameUpper);
        this.classNameUpper = StringUtil.upperName(this.classNameUpper);
        this.serviceUpper = this.classNameUpper + services;
        this.serviceLower = this.classNameLower + services;
        this.interfaceName = I + this.serviceUpper;
        this.apiClassName = classNameUpper + api;
    }

    public Generate() {
        super();
    }

    /**
     * 模块名称
     */
    private String model;
    /**
     * 大写 类名
     */
    private String classNameUpper;
    /**
     * 小写 类名
     */
    private String classNameLower;
    /**
     * 实现类（大写）
     */
    private String serviceUpper;
    /**
     * 实现类（小写）
     */
    private String serviceLower;

    /**
     * 全大写类名
     */
    private String classNameUpperAll;
    /**
     * 数据库表名
     */
    private String tableName;
    /**
     * 实体类字段属性
     */
    private String field;
    /**
     * 实体类导入资源
     */
    private String importList;
    /**
     * 自增编号(大写)sql.xml
     */
    private String filedIdUpper;
    /**
     * 自增编号(小写)sql.xml
     */
    private String filedIdLower;
    /**
     * sql.xml 字段 大写
     */
    private String filedListUpper;
    /**
     * sql.xml 实体类属性 小写
     */
    private String filedListLower;
    /**
     * 接口名称
     */
    private String interfaceName;
    /**
     * api 类名
     */
    private String apiClassName;
    /**
     * 大写 类名
     */
    private String classNameUpperDto;
    /**
     * 小写 类名
     */
    private String classNameLowerDto;

    private final String api = "Api";
    private final String services = "Service";
    private final String I = "I";

    public String getClassNameUpperDto() {
        return classNameUpper + Constant.ENTITY_DTO;
    }

    public String getClassNameLowerDto() {
        return classNameLower + Constant.ENTITY_DTO;
    }

    public String getTableComment() {
        try {
            return JdbcUtil.getTableComments(this.tableName);
        } catch (Exception e) {
           log.error("获取表备注失败");
        }
        return this.tableName;
    }
}
