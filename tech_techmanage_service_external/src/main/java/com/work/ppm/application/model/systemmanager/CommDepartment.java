package com.work.ppm.application.model.systemmanager;

import com.work.ppm.commons.annotaion.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 部门
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommDepartment {
    /**
     * 部门代码
     **/
    @Id
    private Long depid;
    /**
     * 部门编码
     * (十位)
     **/
    private String depcode;
    /**
     * 中文名称
     **/
    private String location;
    /**
     * 部门级别
     **/
    private String depgrade;
    /**
     * 所属机构
     **/
    private Long compid;
    /**
     * 所属上级部门
     **/
    private Long adminid;
    /**
     * 生效日期
     **/
    private Date effectdate;
    /**
     * 是否失效
     **/
    private String isdisabled;
    /**
     * 失效日期
     **/
    private Date disableddate;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 0.hr同步 1.本地新增 2.本地删除
     **/
    private String updateflg;
    /**
     * 最后更新时间
     **/
    private Date lastupdatedate;
    /**
     * 部门代码（HR）
     **/
    private Long depidhr;
    /**
     * 部门负责人
     **/
    private Long director;
    /**
     * 部门分管领导
     **/
    private Long depcharge;
    /**
     * 字段名
     **/
    private String displaycode;
}
