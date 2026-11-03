package com.work.ppm.application.dto.rdmanager;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.work.ppm.application.model.rdmanager.RdProjectUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 项目详情
 *
 */
@Data
public class RdProjectDetailsDto {

    /**
     * 主键
     */
    private String pkId;

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
    private String deptNo;

    /**
     * 系统编号
     */
    @ApiModelProperty(value = "系统编号")
    private String sysNo;

    /**
     * 系统名称
     */
    @ApiModelProperty(value = "系统名称")
    private String sysName;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private String startDate;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private String endDate;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date updateDate;

    /**
     * 项目状态  0  立项中、 1 进行中、 2 收尾中、9 已完结、 3 暂停、 4 废止
     */
    @ApiModelProperty(value = "项目状态  0  立项中、 1 进行中、 2 收尾中、9 已完结、 3 暂停、 4 废止")
    private String projectState;

    /**
     * 项目负责人
     */
    @ApiModelProperty(value = "项目负责人工号")
    private String headUser;
    /**
     * 项目负责人
     */
    @ApiModelProperty(value = "项目负责人")
    private String headUserName;

    /**
     * 研发负责人
     */
    @ApiModelProperty(value = "研发负责人工号")
    private String devUser;

    /**
     * 研发负责人
     */
    @ApiModelProperty(value = "研发负责人")
    private String devUserName;

    /**
     * 是否删除 0 正常  1删除
     */
    @ApiModelProperty(value = "是否删除 0 正常  1删除")
    private String deleted;

    /**
     * 人员列表
     */
    private List<RdProjectUser> userList;


}
