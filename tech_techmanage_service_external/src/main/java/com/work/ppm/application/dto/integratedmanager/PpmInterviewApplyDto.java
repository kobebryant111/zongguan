package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.List;

/**
 * dto
 *
 */
@ExcelTarget("ppmInterviewApplyDto")
@Data
public class PpmInterviewApplyDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 部门编号
     */
    private String deptno;
    /**
     * 部门
     */
    @Excel(name = "部门", orderNum = "5")
    private String deptname;
    /**
     * 流程编号
     */
    private String instanceid;
    /**
     * 意见
     */
    private String applyer;
    /**
     * 新增时间
     */
    @Excel(name = "新增时间", orderNum = "1")
    private String createtime;
    /**
     * 角色id
     */
    private Integer rlId;
    /**
     * 当前操作用户
     */
    private String currentUserId;
    /**
     * 面试官
     */
    private List<PpmInterviewerInfoDto> erList;
    /**
     * 外包人员
     */
    private List<PpmIntervieweeInfoDto> eeList;
    /**
     * 面试人名称
     */
    @Excel(name = "姓名", orderNum = "2")
    private String msname;
    /**
     * 身份证
     */
    @Excel(name = "身份证号", orderNum = "3")
    private String idcard;
    /**
     * 面试结果 0：通过1：不通过
     */
    @Excel(name = "结果", orderNum = "7")
    private String interviewresult;
    /**
     * 性别
     */
    private String sex;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 建议等级
     */
    @Excel(name = "等级", orderNum = "10")
    private String suggestlevel;
    /**
     * 工作年限
     */
    @Excel(name = "年限", orderNum = "9")
    private String workyears;
    /**
     * 岗位
     */
    @Excel(name = "人员类型", orderNum = "6")
    private String position;
    /**
     * 建议单价
     */
    @Excel(name = "单价", orderNum = "11")
    private String suggestprice;
    /**
     * 是否入职
     */
    @Excel(name = "状态", orderNum = "8")
    private String isrz;
    /**
     * 简历
     */
    private String jl;
    /**
     * 成绩
     */
    private Double score;
    /**
     * 外包商编号
     */
    private String wbsid;
    /**
     * 外包商名称
     */
    @Excel(name = "外包商", orderNum = "4")
    private String outsourcname;

    /**
     * 是否符合要求（0：否，1：是）
     */
    private Integer meet;
    /**
     * 不符合原因
     */
    private String remarks;
    /**
     * 人员属性
     */
    private String researchers;
    /**
     * 角色类型 1:管理员，2：总经理，3：综合部经理层，4：二级部经理层，0：普通行员
     */
    private Integer roleType;
}
