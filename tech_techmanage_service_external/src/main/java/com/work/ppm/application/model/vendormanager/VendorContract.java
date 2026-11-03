package com.work.ppm.application.model.vendormanager;

import com.work.ppm.commons.annotaion.Id;
import com.work.ppm.commons.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 合同基本信息
 * </p>
 * @since 2022-10-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_VENDOR_CONTRACT")
public class VendorContract extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 合同编号
     */
    @NotBlank
    @Length(max=50,message = "合同编号格式不符")
    private String contractNo;

    /**
     * 合同名称
     */
    @NotBlank
    @Length(max=50,message = "合同名称格式不符")
    private String contractName;

    /**
     * 供应商
     */
    @NotBlank
    @Length(max=50,message = "供应商格式不符")
    private String vendor;

    /**
     * 供应商名称
     */
    @Length(max=50,message = "供应商格式不符")
    private String vendorName;
    /**
     * 合同生效日期
     */
    private String startDate;

    /**
     * 合同失效日期
     */
    private String endDate;

    /**
     * 合同状态
     */
    private String status;
    /**
     * 附件关联信息：总办议案
     */
    // @Length(max=100,message = "超出字符限制")
    private String attachInfo;

    /**
     * 外包方式 人力:1 / 项目:0
     */
    private String outsourceType;
    /**
     * 税率
     */
    @NotNull
    @Digits(integer = 10,fraction = 11)
    private Double taxRate;

    /**
     * 逻辑删除
     */
    private String isDel;

    /**
     * 进度
     */
    private String progress;

    /**
     * 项目内容
     */
    @Length(max=200,message = "文档内容>200字符")
    private String proContent;
    /**
     * 结算频率
     */

    private String settlementFrequency;
    /**
     * doc
     */
    @NotBlank
    @Length(max=220,message = "超过文档上限")
    private String contractDoc;
    /**
     * 收款人
     */
    @NotBlank
    @Length(max=100,message = "付款人格式不符合")
    private String payee;
    /**
     * 开户行
     */
    @NotBlank
    @Length(max=100,message = "开户行格式不符合")
    private String bankDeposit;
    @NotBlank
    @Length(max=30,message = "账号格式不符合")
    private String account;
    /**
     * 交易类型
     */

    private String transactionType;
    /**
     * 行号
     */
    @NotBlank
    @Length(max=50,message = "行号格式不符合")
    private String bankCode;

}