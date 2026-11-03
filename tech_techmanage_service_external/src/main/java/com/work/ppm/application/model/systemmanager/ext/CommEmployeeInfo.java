package com.work.ppm.application.model.systemmanager.ext;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import lombok.Data;

import java.util.Date;

/**
 * 扩展
 */
@Data
@ExcelTarget("commEmployeeInfo")
public class CommEmployeeInfo extends CommEmployee {
    /**
     * 所属机构
     */
    private String compName;
    /**
     * 所属部门
     */
    @Excel(name = "所属部门", orderNum = "9")
    private String chargeName;
    /**
     * 所属岗位
     */
    private String comorgName;
    /**
     * 小组名称
     */
    private String groupname;
    /**
     * 小组编号
     */
    private String groupId;
    /**
     * 用户名称
     */
    @Excel(name = "姓名", orderNum = "2")
    private String username;
    /**
     * 员工id
     */
    private String userid;
    /**
     * 入职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date entryTime;
    /**
     * 离职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leavingTime;
    /**
     * 外包方式 人力、项目
     */
    private String outsourceTypeName;
    /**
     * 行内负责人
     */
    @Excel(name = "项目经理", orderNum = "10")
    private String empName;
    /**
     * 行内负责人工号
     */
    private String personInCharge;
    /**
     * 等级
     */
    private String grade;
    /**
     * 单价
     */
    private String price;
    /**
     * 外包人员状态（0：在岗，1离岗）
     */
    @Excel(name = "状态", orderNum = "6")
    private String state;
    /**
     * 在场状态
     */
    private String presenceState;
    /**
     * 供应商名称
     */
    @Excel(name = "供应商", orderNum = "5")
    private String outsourcname;
    /**
     * 供应商编码
     */
    private String orgcode;

    /**
     * 角色类型
     * 1:有权限
     * 0:无权限
     */
    private Integer roleType;
    /**
     * 上级部门id
     */
    private String adminid;
    /**
     * 定级级别
     * junior-初级
     * intermediate-中级
     * senior-高级
     * expert-资深
     */
    @Excel(name = "级别", orderNum = "3")
    private String evaluationLevel;
    /**
     * 入场时间 yyyy-MM-dd
     */
    @Excel(name = "入场时间", orderNum = "7")
    private String entryTimeFormate;
    /**
     * 离场时间
     */
    @Excel(name = "离场时间", orderNum = "8")
    private String leavingTimeFormate;
    /**
     * 上下班时间 HH:mm:ss-HH:mm:ss
     */
    private String workTime;

}
