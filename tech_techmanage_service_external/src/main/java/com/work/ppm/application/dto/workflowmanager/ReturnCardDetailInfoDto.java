package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.ppm.application.dto.workflowmanager.ext.ReadyParamsDto;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude
public class ReturnCardDetailInfoDto extends ReadyParamsDto {

    /**
     *  编号
     */
    String id;
    /**
     *  系统编号
     */
    String jobId;
    /**
     *  姓名
     */
    String userName;
    /**
     *  身份证号
     */
    String idCardNo;
    /**
     *  电话
     */
    String telephone;
    /**
     *  所属部门编号
     */
    String deptId;
    /**
     *  所属部门名称
     */
    String deptName;
    /**
     *  外包商编码
     */
    String outsourceNo;
    /**
     *  外包商名称
     */
    String outsourceName;
    /**
     *  人员编号
     */
    String virtualMachineId;
    /**
     *  是否需要制卡
     */
    String ifMakeCard;
    /**
     *  门禁卡编号
     */
    String accessCardNumber;
    /**
     *  是否已领用
     */
    String ifReceiveCard;
    /**
     *  领用时间
     */
    Date receiveTime;
    /**
     *  是否已归还
     */
    String ifReturnCard;
    /**
     *  归还时间
     */
    Date returnTime;
    /**
     *  备注
     */
    String remark;

}
