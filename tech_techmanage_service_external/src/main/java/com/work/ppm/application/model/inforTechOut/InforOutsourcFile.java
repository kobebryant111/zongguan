package com.work.ppm.application.model.inforTechOut;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@TableName("INFOR_OUTSOURC_FILE")
public class InforOutsourcFile {
    /**
     * 主键
     */
    @TableId(value = "ID",type = IdType.UUID)
    private String id;
    /**
     * 项目主键
     */
    @NotBlank(message = "关联id不能为空")
    @TableField
    private String outReqid;
    /**
     * 文件名称
     */
    @TableField
    private String fileName;

    /**
     * 文件名称
     */
    @TableField
    private String sourceFileName;

    /**
     * 文件路径
     */
    @TableField
    private String filePath;

    /**
     * 创建人
     */
    @TableField(fill= FieldFill.INSERT)
    private String createBy;
    /**
     * 创建人
     */
    @TableField(fill= FieldFill.INSERT)
    private String createBadge;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill= FieldFill.INSERT)
    private Date createDate;

    /**
     * 是否删除 0 正常  1删除
     */
    @TableField
    private String deleted;
}
