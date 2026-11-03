package com.work.ppm.application.dto.rdmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 新增研发项目
 *
 */
@Data
public class RdProjectNewDto {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String pkId;

    /**
     * 项目编码
     */
    @NotBlank(message = "项目编码不为空")
    @ApiModelProperty(value = "项目编码")
    private String projectCode;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不为空")
    @ApiModelProperty(value = "项目名称")
    private String projectName;

//    /**
//     * 所属部门
//     */
//    @NotBlank(message = "所属部门不为空")
//    @ApiModelProperty(value = "所属部门")
//    private String deptNo;
//
//    /**
//     * 系统编号
//     */
//    @NotBlank(message = "系统编号不为空")
//    @ApiModelProperty(value = "系统编号")
//    private String sysNo;

    /**
     * 系统名称
     */
    @ApiModelProperty(value = "关联系统")
    private String sysName;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 项目状态
     */
    @ApiModelProperty(value = "项目状态")
    private String projectState;

}
