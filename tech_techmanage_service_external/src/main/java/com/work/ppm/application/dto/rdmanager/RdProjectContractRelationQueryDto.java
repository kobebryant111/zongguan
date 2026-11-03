package com.work.ppm.application.dto.rdmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 关联项目
 *
 */
@Data
public class RdProjectContractRelationQueryDto  {


    /**
     * 项目id
     */
    @NotBlank(message = "项目id不能为空")
    private String rdProjectId;

    /**
     * 合同id
     */
    @NotBlank(message = "合同id不能为空")
    @ApiModelProperty(value = "合同id")
    private String rdProjectContractId;



}
