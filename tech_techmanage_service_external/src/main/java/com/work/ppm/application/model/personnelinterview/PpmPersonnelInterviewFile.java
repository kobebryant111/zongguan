package com.work.ppm.application.model.personnelinterview;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 人员约谈文件表
 *
 */
@Data
@TableName("PPM_PERSONNEL_INTERVIEW_FILE")
public class PpmPersonnelInterviewFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "PK_ID",type = IdType.UUID)
    private String pkId;

    /**
     * 项目主键
     */
    @NotBlank(message = "关联id不能为空")
    @TableField(value = "PERSONNEL_INTERVIEW_ID")
    private String personnelInterviewId;

    /**
     * 类型 0 约谈照片 1 约谈文档
     */
    @TableField(value = "TYPE")
    private String type;


    /**
     * 文件名称
     */
    @TableField(value = "FILE_NAME")
    private String fileName;

    /**
     * 文件路径
     */
    @TableField(value = "FILE_PATH")
    private String filePath;


    /**
     * 创建人
     */
    @TableField(value = "CREATE_BY", fill= FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "CREATE_DATE", fill= FieldFill.INSERT)
    private Date createDate;

    /**
     * 是否删除 0 正常 1 删除
     */
    @TableField(value = "DELETED")
    private String deleted;


}
