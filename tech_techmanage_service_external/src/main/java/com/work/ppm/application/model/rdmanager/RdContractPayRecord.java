package com.work.ppm.application.model.rdmanager;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 合同支付记录
 *
 */
@Data
@Builder
public class RdContractPayRecord {

    /**
     * 主键
     */
    @TableId(value = "PK_ID", type = IdType.UUID)
    private String pkId;

    /**
     * 合同id
     */
    @NotBlank(message = "项目合同id不能为空")
    @ApiModelProperty(value = "合同id")
    private String rdProjectContractId;

    /**
     * 付款编号
     */
    @NotBlank(message = "付款编号不能为空")
    @ApiModelProperty(value = "付款编号")
    @TableField
    private String serial;
    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态")
    @TableField
    private String payment;
    /**
     * 完成时间
     */
    @ApiModelProperty(value = "完成时间")
    @TableField
    private String comptime;
    /**
     * 支付次数
     */
    @ApiModelProperty(value = "支付次数")
    @NotBlank(message = "支付次数不能为空")
    @TableField
    private String payNum;


    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    @NotBlank(message = "支付金额不能为空")
    @TableField
    private String payAmount;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField
    private String remark;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;

}
