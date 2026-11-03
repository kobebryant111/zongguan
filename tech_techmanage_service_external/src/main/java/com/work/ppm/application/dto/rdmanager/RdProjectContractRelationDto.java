package com.work.ppm.application.dto.rdmanager;

import com.work.ppm.application.model.rdmanager.RdProjectContractRelation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 关联项目
 *
 */
@Data
public class RdProjectContractRelationDto extends RdProjectContractRelation {


    /**
     * 项目代码
     */
    @ApiModelProperty(value = "项目代码")
    private String projectCode;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String projectName;



}
