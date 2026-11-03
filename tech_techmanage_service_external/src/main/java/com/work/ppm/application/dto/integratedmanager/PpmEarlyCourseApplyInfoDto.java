package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import lombok.Data;

import java.util.Date;

/**
 * 提前入场申请信息dto
 */
@Data
public class PpmEarlyCourseApplyInfoDto {
    /**
     *	id
     **/
    private Long id;
    /**
     *	pid
     **/
    private Long pid;
    /**
     *	流程id
     **/
    private String instanceid;
    /**
     *	关联面试编号
     **/
    private String interviewId;
    /**
     *	姓名
     **/
    @Excel(name = "姓名" , orderNum = "1")
    private String userName;
    /**
     *	提前入场时间
     **/
    @Excel(name = "提前入场时间",orderNum = "8",exportFormat = "yyyy-MM-dd")
    private Date earlyEntryTime;
    /**
     *	是否补全入场材料
     **/
    @Excel(name = "是否补全入场材料",orderNum = "9",replace = {"是_Y","否_N"})
    private String isComplement;
    /**
     *	备注
     **/
    private String remark;
    /**
     *	创建时间
     **/
    private Date createdTime;
    /**
     *	创建人
     **/
    private String createdBy;
    /**
     * 修改时间
     */
    private Date modifiedTime;
    /**
     * 修改人
     */
    private String modifiedBy;
    /**
     * bps
     */
    private BpsWorkFlowDto bps;
    /**
     * 部门编号
     */
    private String deptNo;
    /**
     * 申请人
     */
    private String applyer;
    /**
     * 申请人名称
     */
    @Excel(name = "申请人名称",orderNum = "3")
    private String applyerName;
    /**
     * 证件号
     */
    private String cardNo;
    /**
     * 部门名称
     */
    @Excel(name = "所属部门",orderNum = "2")
    private String deptName;
    /**
     * 人员类型
     */
    @Excel(name = "人员类型",orderNum = "4")
    private String position;
    /**
     * 供应商编号
     */
    private String wbsId;
    /**
     * 供应商名称
     */
    @Excel(name = "所属供应商",orderNum = "7")
    private String outSourceName;
    /**
     * 年限
     */
    @Excel(name = "工作年限",orderNum = "5")
    private String workyears;
    /**
     * 单价
     */
    @Excel(name = "预计单价（元/人月）",orderNum = "6")
    private String suggestprice;

    /**
     * 部门id 查询用
     */
    private String deptId;

    /**
     * 催办时间
     */
    private Date campaignTime;

    /**
     * 催办时间 查询用
     */
    private String campaignTimeVal;
    /**
     * 申请人 筛选
     */
    private String applyerValue;
    /**
     * 删除筛选
     */
    private String cardNoDel;

    /**
     * 权限查询 部门编号
     */
    private String deptNoQue;

    /**
     * 是否可修改
     */
    private String isUpdate;

}
