package com.work.ppm.application.dto.rdmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 新增研发项目
 *
 */
@Data
public class RdProjectInnerDto {


    /**
     * 组件
     */
    @NotBlank(message = "主键不为空")
    @ApiModelProperty(value = "主键")
    private String pkId;

    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String competentDept;

    /**
     * 参与部门
     */
    @ApiModelProperty(value = "参与部门")
    private String participatingDept;

    /**
     * 项目负责人
     */
    @ApiModelProperty(value = "项目负责人")
    private String headUser;

    /**
     * 研发负责人
     */
    @ApiModelProperty(value = "研发负责人")
    private String devUser;

}
