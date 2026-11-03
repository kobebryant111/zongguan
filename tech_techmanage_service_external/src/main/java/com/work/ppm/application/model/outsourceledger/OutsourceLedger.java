package com.work.ppm.application.model.outsourceledger;

import com.work.ppm.commons.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.work.ppm.commons.annotaion.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 * <p>
 * 提前入场申请信息表
 * </p>
 * @since 2022-11-22
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_OUTSOURCE_LEDGER")
public class OutsourceLedger extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一编号
     */
    @Id
    private Long id;
    /**
     * 外包项目名称
     */
    private String projectName;
    /**
     * 具体外包事项
     */
    private String descriptions;
    /**
     * 外包业务用工部门
     */
    private String deptNo;
    /**
     * 外包服务提供商
     */
    private String vendor;

    private String deptName;

    private String vendorName;

    /**
     * 服务商注册地（境外、境内）
     */
    private String registerPlace;
    /**
     * 合同起始时间
     */
    private Date startDate;
    /**
     * 合同到期日
     */
    private Date endDate;
    /**
     * 外包项目金额
     */
    private Double amount;
    /**
     * 外包项目人数
     */
    private Double nums;
    /**
     * 逻辑删除
     */
    private String isDel;
    /**
     * 服务方式（是否驻场）
     */
    private String isStation;
    /**
     * 是否分包或转包
     */
    private String isSubcontract;

}