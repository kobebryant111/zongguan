package com.work.ppm.application.dto.rdmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 研发项目关联
 *
 */
@Data
public class ContractResearchProjectDto {

    /**
     * 合同id
     */
    @NotBlank(message = "合同id不能为空")
    @ApiModelProperty(value = "合同id")
    private String rdProjectContractId;

    /**
     * 是否为研发项目
     */
    @NotBlank(message = "是否关联研发项目")
    @ApiModelProperty(value = "是否为研发项目")
    private String isResearchProject;

    /**
     * 科技项目关键技术点/创新点
     */
    @ApiModelProperty(value = "科技项目关键技术点/创新点")
    private String techInnoPoint;

    /**
     * 研发项目
     */
    @ApiModelProperty(value = "研发项目")
    private String rdProjectId;



}
