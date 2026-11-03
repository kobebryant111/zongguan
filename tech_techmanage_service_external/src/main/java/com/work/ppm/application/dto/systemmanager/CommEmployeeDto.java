package com.work.ppm.application.dto.systemmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 员工
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget("commEmployeeDto")
public class CommEmployeeDto {
    /**
     * 性别
     **/
    private String gender;
    /**
     * 0.HR数据  1.本地新增  2.本地编辑
     **/
    private String updateflg;
    /**
     * 最新更新时间
     **/
    private Date lastupdatedate;
    /**
     * 上级领导
     **/
    private Integer reportto;
    /**
     * 删除标识
     **/
    private String isdisabled;
    /**
     * 短号
     **/
    private String shortphone;
    /**
     * 姓名
     * 行内负责人
     **/
    @Excel(name = "行内负责人", orderNum = "8")
    private String empname;
    /**
     * 员工编制
     **/
    private String empauth;
    /**
     * 出生日期
     **/
    private Date birthday;
    /**
     * 编制属性
     **/
    private String authproperty;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 字段名
     **/
    private String fullname;
    /**
     * 所属岗位代码
     **/
    private Integer jobid;
    /**
     * 员工代码
     **/
    private Integer eid;
    /**
     * 工号
     **/
    @Excel(name = "工号", orderNum = "1")
    private String badge;
    /**
     * 所属机构代码
     **/
    private Integer compid;
    /**
     * 员工状态
     **/
    private String empstatus;
    /**
     * 办公电话
     **/
    private String officephone;
    /**
     * 员工代码HR
     **/
    private Integer eidhr;
    /**
     * 所属条线
     **/
    private String boardline;
    /**
     * 字段名
     **/
    private String spellname;
    /**
     * 所属部门代码
     **/
    private Integer depid;
    /**
     * 职务
     **/
    private String headship;
    /**
     * 移动电话
     **/
    @Excel(name = "电话", orderNum = "3")
    private String mobiletelphone;
    /**
     * 角色id
     */
    private String rolId;
    /**
     * 小组名称
     */
    @Excel(name = "所在小组", orderNum = "7")
    private String groupname;
    /**
     * 小组编号
     */
    private String groupId;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 入职时间
     */
    private String[] entryTime;
    /**
     * 离职时间
     */
    private String[] leavingTime;
    /**
     * 入职开始时间
     */
    private Date entryStartDate;
    /**
     * 入职结束时间
     */
    private Date entryEndDate;
    /**
     * 离职开始时间
     */
    private Date leaveStartDate;
    /**
     * 离职结束时间
     */
    private Date leaveEndDate;
    /**
     * 入参属性
     */
    private String dep;
    /**
     * 入参属性
     * 新增 -- 外包人员状态（0：在岗，1离岗） k6991 2021-01-27
     */
    @Excel(name = "状态", orderNum = "4")
    private String state;
    /**
     * 入参属性 预加载数
     */
    private Integer proLoadNumber;
    /**
     * 所属机构
     */
    private String compName;
    /**
     * 所属部门
     */
    @Excel(name = "所属部门", orderNum = "6")
    private String chargeName;
    /**
     * 所属岗位
     */
    private String comorgName;
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
     * 外包方式 人力、项目
     */
    @Excel(name = "类型", orderNum = "5")
    private String outsourceTypeName;
    /**
     * 行内负责人
     */
    private String empName;

    /**
     * 行内负责人工号
     */
    private String personInCharge;

    /**
     * 系统类别
     * 1:新应用,2:供应商管理系统
     */
    private Integer sysType;
    /**
     * 在场状态
     */
    private String presenceState;
    /**
     * 供应商名称
     */
    private String outsourcname;

    /**
     * 查询多个部门入参属性
     */
    private String deps;

    /**
     * 证件类型
     **/
    private String certificateType;
    /**
     * 证件号码
     **/
    private String certificateNumber;

    /**
     * 部门父编号
     */
    private String adminid;

    private String roleId;

    /**
     * 部门编号
     */
    private String depcode;
    /**
     * 部门中文名称
     */
    private String location;
    /**
     * 供应商编码
     */
    private String orgcode;
}
