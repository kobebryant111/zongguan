package com.work.ppm.application.dto.rdmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增研发项目
 *
 */
@Data
public class RdProjectContractQueryDto {


    /**
     * 项目编码
     */
    @ApiModelProperty(value = "项目编码")
    private String contractCode;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    /**
     * 合同名称
     */
    @ApiModelProperty(value = "合同名称")
    private String contractName;

    /**
     * 预算编号
     */
    @ApiModelProperty(value = "预算编号")
    private String budgetCode;

    /**
     * 预算名称
     */
    @ApiModelProperty(value = "预算名称")
    private String budgetName;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String prop;
    /**
     * 排序方式
     */
    @ApiModelProperty(value = "排序方式")
    private String order;
}
