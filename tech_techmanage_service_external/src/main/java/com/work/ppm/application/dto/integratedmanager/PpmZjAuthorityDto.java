package com.work.ppm.application.dto.integratedmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 闸机权限管理 传输对象
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PpmZjAuthorityDto {
    /**
     * 主键
     **/
    @ApiModelProperty(name = "id", value = "主键", dataType = "Long")
    private Long id;
    /**
     * 流程实例ID
     **/
    @ApiModelProperty(name = "instanceid", value = "流程实例ID", dataType = "String")
    private String instanceid;
    /**
     * 闸机ID
     **/
    @ApiModelProperty(name = "ppmZjId", value = "闸机ID", dataType = "Long")
    private String ppmZjId;
    /**
     * 闸机
     **/
    @ApiModelProperty(name = "zjName", value = "闸机", dataType = "String")
    private String zjname;
    /**
     * 工号
     **/
    @ApiModelProperty(name = "badge", value = "工号", dataType = "String")
    private String badge;
    /**
     * 姓名
     **/
    @ApiModelProperty(name = "empname", value = "姓名", dataType = "String")
    private String empname;
    /**
     *	证件类型
     **/
    @ApiModelProperty(name = "certificateType", value = "证件类型", dataType = "String")
    private String certificateType;
    /**
     *	证件号码
     **/
    @ApiModelProperty(name = "certificateNumber", value = "证件号码", dataType = "String")
    private String certificateNumber;
    /**
     * 所属部门
     **/
    @ApiModelProperty(name = "depid", value = "所属部门", dataType = "Long")
    private Long depid;
    /**
     * 员工状态
     **/
    @ApiModelProperty(name = "empstatus", value = "员工状态", dataType = "String")
    private String empstatus;
    /**
     * 申请类型
     **/
    @ApiModelProperty(name = "applyType", value = "申请类型", dataType = "Long")
    private Integer applyType;
    /**
     * 开始时间
     **/
    @ApiModelProperty(name = "beginDate", value = "开始时间", dataType = "Date")
    private Date beginDate;
    /**
     * 结束时间
     **/
    @ApiModelProperty(name = "endDate", value = "结束时间", dataType = "Date")
    private Date endDate;
    /**
     * 状态（-1:已失效,0:待生效，1:已生效）
     **/
    @ApiModelProperty(name = "flag", value = "状态（-1:已失效,0:待生效，1:已生效）", dataType = "Long")
    private Integer flag;

    /**
     * 开始时间
     **/
    private Date entryStartDate;
    /**
     * 结束时间
     */
    private Date entryEndDate;
}
