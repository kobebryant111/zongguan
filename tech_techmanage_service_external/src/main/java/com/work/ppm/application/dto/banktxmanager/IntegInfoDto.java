package com.work.ppm.application.dto.banktxmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 行员调休信息表 传输对象
 *
 */
@Data
public class IntegInfoDto {
    /**
     * 主键 DETAIL_ID
     **/
    @ApiModelProperty(name = "detailId", value = "唯一编号", dataType = "String")
    private String  detailId;
    /**
     * 姓名 EMP_NAME
     **/
    @ApiModelProperty(name = "empName", value = "姓名", dataType = "String")
    private String empName;
    /**
     * 工号 BADGE
     **/
    @ApiModelProperty(name = "badge", value = "工号", dataType = "String")
    private String badge;
    /**
     * 登记日期 REGISTER_DATA
     **/
    @ApiModelProperty(name = "registerData", value = "登记日期", dataType = "String")
    private String registerData;
    /**
     * 日期类型 DATA_TYPE
     **/
    @ApiModelProperty(name = "dataType", value = "日期类型", dataType = "String")
    private String dataType;
    /**
     * 开始时间 START_TIME
     **/
    @ApiModelProperty(name = "startTime", value = "开始时间", dataType = "String")
    private String startTime;
    /**
     * 结束时间 END_TIME
     **/
    @ApiModelProperty(name = "endTime", value = "结束时间", dataType = "String")
    private String endTime;
    /**
     * 产生调休 INTEG_NUM
     **/
    @ApiModelProperty(name = "integNum", value = "产生调休", dataType = "String")
    private String integNum;
    /**
     * 工作分类 WORK_TYPE
     **/
    @ApiModelProperty(name = "workType", value = "工作分类", dataType = "String")
    private String workType;
    /**
     * 原因 INTEG_REASON
     **/
    @ApiModelProperty(name = "integReason", value = "原因", dataType = "String")
    private String integReason;
    /**
     * 退回原因 INTEG_BACK_REASON
     */
    @ApiModelProperty(name = "integBackReason", value = "退回原因", dataType = "String")
    private String integBackReason;
    /**
     * 当前审批人 INTEG_CURRENT_APPROVER
     */
    @ApiModelProperty(name = "integCurrentApprover", value = "当前审批人", dataType = "String")
    private String integCurrentApprover;
    /**
     * 审批时间 INTEG_APPROVER_TIME
     */
    @ApiModelProperty(name = "integApproverTime", value = "审批时间", dataType = "String")
    private String integApproverTime;
    /**
     * 创建时间 CREATE_TIME
     */
    @ApiModelProperty(name = "createTime", value = "创建时间", dataType = "String")
    private String createTime;
    /**
     * 修改时间 UPDATE_TIME
     */
    @ApiModelProperty(name = "updateTime", value = "修改时间", dataType = "String")
    private String updateTime;
    /**
     * 状态 STATE
     */
    @ApiModelProperty(name = "state", value = "状态", dataType = "String")
    private String state;
    /**
     * 时间区间
     */
    private String[] entryTime;
    /**
     *开始时间
     **/
    private Date entryStartDate;
    /**
     * 结束时间
     */
    private Date entryEndDate;
    /**
     * 姓名工号
     */
    private String nameBadge;
    /**
     * 主键集合
     */
    private String ids;
}
