package com.work.ppm.application.model.inforTechOut;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * PO对象
 *
 */
@Data
@TableName("XK_MULTI_SEQUENCE")
public class XJMultiSequencePO {

    /**
     * 类型
     */
    @TableField("SEQTYPE")
    private String seqtype;

    /**
     * 数字
     */
    @TableField("SEQID")
    private String seqid;

    /**
     * 前缀
     */
    @TableField("SEQPREFIX")
    private String seqprefix;
}
