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
 * 供应商合同信息表
 * </p>
 *
 * @since 2026-03-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ppm.PPM_SUPPLIER_CONTRACT")
@KeySequence("ppm.SEQ_PPM_SUPPLIER_CONTRACT")
public class PpmSupplierContract implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId(value = "ID", type = IdType.NONE)
    private Long id;

    /**
     * 供应商编号
     */
    @TableField("SUPPLIER_ID")
    private String supplierId;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号", orderNum = "2")
    @TableField("CONTRACT_NO")
    private String contractNo;

    /**
     * 合同名称
     */
    @Excel(name = "合同名称", orderNum = "1")
    @TableField("CONTRACT_NAME")
    private String contractName;

    /**
     * 合同金额
     */
    @TableField("CONTRACT_AMT")
    private BigDecimal contractAmt;

    /**
     * 合同开始时间
     */
    @TableField("CONTRACT_BEGIN_DATE")
    private Date contractBeginDate;

    /**
     * 合同结束时间
     */
    @TableField("CONTRACT_END_DATE")
    private Date contractEndDate;

    /**
     * 合同生效状态：P-待生效 Y-生效中 N-已过期
     */
    @TableField("CONTRACT_STATE")
    private String contractState;

    /**
     * 初级开发人数
     */
    @TableField("JUNIOR_NUM")
    private Integer juniorNum;

    /**
     * 中级开发人数
     */
    @TableField("INTERMEDIATE_NUM")
    private Integer intermediateNum;

    /**
     * 高级开发人数
     */
    @TableField("SENIOR_NUM")
    private Integer seniorNum;

    /**
     * 资深开发人数
     */
    @TableField("EXPERT_NUM")
    private Integer expertNum;

    /**
     * 初级开发单价
     */
    @TableField("JUNIOR_PRICE")
    private BigDecimal juniorPrice;

    /**
     * 中级开发单价
     */
    @TableField("INTERMEDIATE_PRICE")
    private BigDecimal intermediatePrice;

    /**
     * 高级开发单价
     */
    @TableField("SENIOR_PRICE")
    private BigDecimal seniorPrice;

    /**
     * 资深开发单价
     */
    @TableField("EXPERT_PRICE")
    private BigDecimal expertPrice;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_DATE")
    private Date createDate;

    /**
     * 修改时间
     */
    @TableField(value = "UPDATE_DATE")
    private Date updateDate;


}
