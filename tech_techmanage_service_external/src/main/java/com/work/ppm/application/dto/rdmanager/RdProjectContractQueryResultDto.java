package com.work.ppm.application.dto.rdmanager;

import com.work.ppm.application.model.rdmanager.RdProjectContract;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增研发项目
 *
 */
@Data
public class RdProjectContractQueryResultDto extends RdProjectContract {


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
