package com.work.ppm.application.model.rdmanager;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * RdProjectUser
 *
 */
@Data
public class RdProjectUser {

    /**
     * 主键
     */
    @TableId(value = "PK_ID",type = IdType.UUID)
    private String pkId;

    /**
     * 项目主键
     */
    @NotBlank(message = "项目id不能为空")
    @TableField
    private String rdProjectId;

    /**
     * 部门
     */
    @NotBlank(message = "部门不能为空")
    @TableField
    private String deptName;


    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称不能为空")
    @TableField
    private String userName;


    /**
     * 用户名称
     */
    @NotBlank(message = "用户工号不能为空")
    @TableField
    private String userId;

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

}
