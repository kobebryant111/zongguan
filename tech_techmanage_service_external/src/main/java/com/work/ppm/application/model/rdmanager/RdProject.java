package com.work.ppm.application.model.rdmanager;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 研发项目管理
 *
 */
@Data
public class RdProject {

    /**
     * 主键
     */
    @TableId(value = "PK_ID",type = IdType.UUID)
    private String pkId;

    /**
     * 项目编码
     */
    @TableField
    private String projectCode;

    /**
     * 项目名称
     */
    @TableField
    private String projectName;

    /**
     * 所属部门
     */
    @TableField
    private String competentDept;
    /**
     * 所属部门名称
     */
    @TableField(exist = false)
    private String competentName;
    /**
     * 参与部门
     */
    @TableField
    private String participatingDept;

    /**
     * 系统编号
     */
    @TableField
    private String sysNo;

    /**
     * 系统名称
     */
    @TableField
    private String sysName;
    /**
     * 开始时间
     */
    @TableField
    private String startDate;

    /**
     * 结束时间
     */
    @TableField
    private String endDate;

    /**
     * 创建人
     */
    @TableField(fill= FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill= FieldFill.INSERT)
    private Date createDate;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;

    /**
     * 项目状态  0  立项中、 1 进行中、 2 收尾中、9 已完结、 3 暂停、 4 废止
     */
    @TableField
    private String projectState;

    /**
     * 项目负责人
     */
    @TableField
    private String headUser;

    /**
     * 研发负责人
     */
    @TableField
    private String devUser;

    /**
     * 是否删除 0 正常  1删除
     */
    @TableField
    private String deleted;

    /**
     * 业务创新点
     */
    @TableField
    private String busInnova;

    /**
     * 技术创新点
     */
    @TableField
    private String techInnova;




}
