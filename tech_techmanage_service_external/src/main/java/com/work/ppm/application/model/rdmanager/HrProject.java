package com.work.ppm.application.model.rdmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.ppm.application.model.administration.WorkResearchFile;
import com.work.ppm.application.model.integratedmanager.PpmKqCalendar;
import com.work.ppm.application.model.integratedmanager.PpmKqRdCalendar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HrProject {

    /**
     * 主键
     */
    @TableId(value = "PK_ID", type = IdType.UUID)
    private String pkId;

    /**
     * 项目编码
     */
    @TableField
    private String projectCode;

    /**
     * 项目名称
     */
    @TableField
    private String projectName;

    /**
     * 所属部门
     */
    @TableField
    private String competentDept;

    /**
     * 参与部门
     */
    @TableField
    private String participatingDept;

    /**
     * 系统编号
     */
    @TableField
    private String sysNo;

    /**
     * 系统名称
     */
    @TableField
    private String sysName;
    /**
     * 开始时间
     */
    @TableField
    private String startDate;

    /**
     * 结束时间
     */
    @TableField
    private String endDate;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;

    /**
     * 项目状态  0  立项中、 1 进行中、 2 收尾中、9 已完结、 3 暂停、 4 废止
     */
    @TableField
    private String projectState;

    /**
     * 项目负责人
     */
    @TableField
    private String headUser;

    /**
     * 研发负责人
     */
    @TableField
    private String devUser;

    /**
     * 是否删除 0 正常  1删除
     */
    @TableField
    private String deleted;
    /**
     * 研发人数量
     */
    @TableField
    private Integer no;
    /**
     * 员工状态
     **/
    @TableField
    private String empstatus;
    /**
     * 业务创新点
     */
    @TableField
    private String busInnova;

    /**
     * 技术创新点
     */
    @TableField
    private String techInnova;

    /**
     * 合同金额
     */
    @TableField
    private String contractAmount;

    /**
     * 合同名称
     */
    @TableField
    private String contractName;
    /**
     * 占比
     */
    @TableField
    private double proportion;

    /**
     * 考勤信息
     */
    @TableField
    private List<PpmKqCalendar> ppmKqCalendarList;

    /**
     * 工号
     **/
    @Excel(name = "工号", orderNum = "1")
    private String badge;

    /**
     * 员工代码
     **/
    private Integer eid;

    /**
     * 用户名称
     */
    @Excel(name = "姓名", orderNum = "2")
    private String username;


    /**
     * 所属部门
     */
    @Excel(name = "所属部门", orderNum = "6")
    private String chargeName;

    /**
     * 代码编号
     */
    @Excel(name = "代码编号", orderNum = "7")
    private String eidCode;

    /**
     * 0.全职 1.兼职1（85%以上)、2兼职研发2(80%以上)、3兼职研发3 (50%-70%)
     */
    private String nature;
    /**
     * 删除标识
     */
    private String isdisabled;
    /**
     * 主键
     */
    private String id;
    /**
     * 所属部门代码
     **/
    private Integer depid;
    /**
     * 日期
     */
    private String attendanceDate;
    /**
     * 考勤状态
     */
    private String auditStatus;
    /**
     * 项目是否已被选择
     */
    private String isChosen;
    /**
     * 考勤填报
     */
    private List<PpmKqRdCalendar> rdCalendarList;
    /**
     * 文件集合
     */
    private List<WorkResearchFile> children;
    /**
     * 到岗日期
     */
    private String arrivalTime;

    /**
     * 离岗日期
     */
    private String leaveTime;

    /**
     * 合同预算总金额
     */
    private String budgetAmount;

    /**
     * 合同预算名称
     */
    private String budgetName;
}
