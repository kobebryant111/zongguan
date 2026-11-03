package com.work.ppm.application.model.banktxmanager;

import com.work.ppm.commons.annotaion.Id;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 行员调休信息表 传输对象
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegInfo {
    /**
     * 主键 DETAIL_ID
     **/
    private String  detailId;
    /**
     * 姓名 EMP_NAME
     **/
    private String empName;
    /**
     * 工号 BADGE
     **/
    private String badge;
    /**
     * 登记日期 REGISTER_DATA
     **/
    private String registerData;
    /**
     * 日期类型 DATA_TYPE
     **/
    private String dataType;
    /**
     * 开始时间 START_TIME
     **/
    private String startTime;
    /**
     * 结束时间 END_TIME
     **/
    private String endTime;
    /**
     * 产生调休 INTEG_NUM
     **/
    private float integNum;
    /**
     * 工作分类 WORK_TYPE
     **/
    private String workType;
    /**
     * 原因 INTEG_REASON
     **/
    private String integReason;
    /**
     * 退回原因 INTEG_BACK_REASON
     */
    private String integBackReason;
    /**
     * 当前审批人 INTEG_CURRENT_APPROVER
     */
    private String integCurrentApprover;
    /**
     * 审批时间 INTEG_APPROVER_TIME
     */
    private String integApproverTime;
    /**
     * 创建时间 CREATE_TIME
     */
    private String createTime;
    /**
     * 修改时间 UPDATE_TIME
     */
    private String updateTime;
    /**
     * 状态 STATE
     */
    private String state;

}
