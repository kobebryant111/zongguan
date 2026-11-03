package com.work.ppm.application.model.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 供应商合同外包关联表
 * </p>
 *
 * @since 2026-03-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("PPM_SUPPLIER_OUTSOURCING")
@KeySequence("SEQ_PPM_SUPPLIER_OUTSOURCING")
public class PpmSupplierOutsourcing implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    /**
     * 工号
     */
    @TableField("JOB_ID")
    @Excel(name = "工号", orderNum = "2")
    private String jobId;

    /**
     * 姓名
     */
    @TableField("USER_NAME")
    @Excel(name = "姓名", orderNum = "3")
    private String userName;

    /**
     * 级别
     */
    @TableField("EVALUATION_LEVEL")
    @Excel(name = "级别", orderNum = "4")
    private String evaluationLevel;

    /**
     * 级别单价
     */
    @TableField("PRICE")
    private BigDecimal price;

    /**
     * 上下班时间
     */
    @TableField("WORK_TIME")
    private String workTime;

    /**
     * 工作开始日期
     */
    @TableField("WORK_BEGIN_DATE")
    private Date workBeginDate;

    /**
     * 工作结束日期 为空默认当前时间
     */
    @TableField("WORK_END_DATE")
    private Date workEndDate;

    /**
     * 关联状态：Y-有效 N-失效
     */
    @TableField("RELATE_STATE")
    private String relateState;

    /**
     * 合同序号
     */
    @TableField("RELATE_ID")
    private String relateId;

    /**
     * 创建时间
     */
    @TableField("CREATE_DATE")
    private Date createDate;

    /**
     * 修改时间
     */
    @TableField("UPDATE_DATE")
    private Date updateDate;


}
