package com.work.ppm.application.model.systemmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.work.ppm.commons.annotaion.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 描述
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class    CommEmployee {
    /**
     * 员工代码
     **/
    @Id
    private Long eid;
    /**
     * 工号
     **/
    @Excel(name = "系统编号", orderNum = "1")
    private String badge;
    /**
     * 姓名
     **/
    private String empname;
    /**
     * 所属机构代码
     **/
    private Long compid;
    /**
     * 所属部门代码
     **/
    private Long depid;
    /**
     * 所属岗位代码
     **/
    private Long jobid;
    /**
     * 职务
     **/
    private String headship;
    /**
     * 员工状态
     **/
    private String empstatus;
    /**
     * 员工编制
     **/
    private String empauth;
    /**
     * 编制属性
     **/
    private String authproperty;
    /**
     * 性别
     **/
    private String gender;
    /**
     * 出生日期
     **/
    private Date birthday;
    /**
     * 办公电话
     **/
    private String officephone;
    /**
     * 移动电话
     **/
    @Excel(name = "电话", orderNum = "4")
    private String mobiletelphone;
    /**
     * 备注
     **/
    private String remark;
    /**
     * 删除标识
     **/
    private String isdisabled;
    /**
     * 0.HR数据  1.本地新增  2.本地编辑
     **/
    private String updateflg;
    /**
     * 最新更新时间
     **/
    private Date lastupdatedate;
    /**
     * 员工代码HR
     **/
    private Long eidhr;
    /**
     * 所属条线
     **/
    private String boardline;
    /**
     * 上级领导
     **/
    private Long reportto;
    /**
     * 字段名
     **/
    private String fullname;
    /**
     * 字段名
     **/
    private String spellname;
    /**
     * 短号
     **/
    private String shortphone;

    /**
     *	证件类型
     **/
    private String certificateType;
    /**
     *	证件号码
     **/
    private String certificateNumber;
    /**
     * 父编号
     */
    private String adminid;
    /**
     * 部门编号
     */
    private String depcode;
    /**
     * 部门中文名称
     */
    private String location;

}
