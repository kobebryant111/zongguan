package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


/**
 * 闸机权限申请表 传输对象
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PpmZjAuthorityApplyDto {
    /**
     * 主键
     **/
    @ApiModelProperty(name = "id", value = "主键", dataType = "Long")
    private Long id;
    /**
     * 字段名
     **/
    @ApiModelProperty(name = "ppmZjId", value = "", dataType = "Long")
    private String ppmZjId;
    /**
     * 流程实例ID
     **/
    @ApiModelProperty(name = "instanceid", value = "流程实例ID", dataType = "String")
    private String instanceid;
    /**
     * 业务类型
     **/
    @ApiModelProperty(name = "bizType", value = "业务类型", dataType = "Long")
    private Integer bizType;
    /**
     * 标题
     **/
    @ApiModelProperty(name = "title", value = "标题", dataType = "String")
    private String title;
    /**
     * 流程ID
     **/
    @ApiModelProperty(name = "flowid", value = "流程ID", dataType = "String")
    private String flowid;
    /**
     * 状态（-1:已取消,0:申请中，1:已完成）
     **/
    @ApiModelProperty(name = "flowflag", value = "状态（-1:已取消,0:申请中，1:已完成）", dataType = "Long")
    private Integer flowflag;
    /**
     * 所属部门
     **/
    @ApiModelProperty(name = "depid", value = "所属部门", dataType = "Long")
    private Long depid;
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
     * 创建人
     **/
    @ApiModelProperty(name = "createid", value = "创建人", dataType = "String")
    private String createid;
    /**
     * 创建时间
     **/
    @ApiModelProperty(name = "createdate", value = "创建时间", dataType = "Date")
    private Date createdate;
    /**
     * 完成时间
     **/
    @ApiModelProperty(name = "completedate", value = "完成时间", dataType = "Date")
    private Date completedate;
    /**
     * bps
     */
    private BpsWorkFlowDto bps;
    /**
     * 闸机权限列表
     */
    private List<PpmZjAuthorityDto> authorities;

    /**
     * 字段名
     **/
    private List<String> ppmZjIdArrays;
}
