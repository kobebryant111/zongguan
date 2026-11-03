package com.work.ppm.application.dto.rdmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增研发项目
 *
 */
@Data
public class RdProjectQueryDto {


    /**
     * 项目编码
     */
    @ApiModelProperty(value = "项目编码")
    private String projectCode;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String projectName;



    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String competentDept;


    /**
     * 系统名称
     */
    @ApiModelProperty(value = "系统名称")
    private String sysName;

    /**
     * 不关联合同id
     */
    @ApiModelProperty(value = "不关联合同id")
    private String notRdProjectContractId;
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
