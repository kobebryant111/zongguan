package com.work.ppm.application.model.personnelinterview;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员约谈表
 *
 */
@Data
@TableName("PPM_PERSONNEL_INTERVIEW")
public class PpmPersonnelInterview implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "PK_ID",type = IdType.UUID)
    private String pkId;

    /**
     * 约谈标题
     */
    @TableField(value = "TITLE")
    private String title;

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
     * 是否删除 0 正常  1删除
     */
    @TableField(value = "DELETED")
    private String deleted;


}
