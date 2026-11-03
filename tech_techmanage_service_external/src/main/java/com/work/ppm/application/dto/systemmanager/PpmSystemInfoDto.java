package com.work.ppm.application.dto.systemmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 系统详情表 传输对象
 *
 */
@Data
public class PpmSystemInfoDto {
    /**
     * 主键
     **/
    @ApiModelProperty(name = "id", value = "主键", dataType = "Long")
    private Long id;
    /**
     * 系统名称
     **/
    @ApiModelProperty(name = "name", value = "系统名称", dataType = "String")
    private String name;
    /**
     * 归属部门ID
     **/
    @ApiModelProperty(name = "depId", value = "归属部门ID", dataType = "Long")
    private Long depId;

    /**
     * 系统负责人工号
     **/
    @ApiModelProperty(name = "badge", value = "系统负责人工号", dataType = "String")
    private String badge;
    /**
     *	系统负责人名称
     **/
    @ApiModelProperty(name = "empName", value = "系统负责人名称", dataType = "String")
    private String empName;
    /**
     * 系统负责人工号
     **/
    @ApiModelProperty(name = "badges", value = "系统负责人工号,支持多选", dataType = "Array")
    private String[] badges;
    /**
     * 创建人
     **/
    @ApiModelProperty(name = "createBy", value = "创建人", dataType = "String")
    private String createBy;
    /**
     * 创建时间
     **/
    @ApiModelProperty(name = "createDate", value = "创建时间", dataType = "Date")
    private Date createDate;
    /**
     * 修改人
     **/
    @ApiModelProperty(name = "updateBy", value = "修改人", dataType = "String")
    private String updateBy;
    /**
     * 修改时间
     **/
    @ApiModelProperty(name = "updateDate", value = "修改时间", dataType = "Date")
    private Date updateDate;
    /**
     * 备注
     **/
    @ApiModelProperty(name = "remark", value = "备注", dataType = "String")
    private String remark;
}
