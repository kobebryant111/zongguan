package com.work.ppm.application.model.vendormanager;

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

/**
 * <p>
 * 单价调整
 * </p>
 * @since 2022-12-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_OUTSRCING_PRICE_CHG")
public class OutsrcingPriceChg extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    private Long id;
    /**
     * 流程实例id
     */
    private String instanceId;
    /**
     * 流程键值
     */
    private String flowId;
    /**
     * 人员CODE
     */
    private String userCode;
    /**
     * 人员NAME
     */
    private String userName;
    /**
     * 所属部门ID
     */
    private String deptId;
    /**
     * 人员类型
     */
    private String position;
    /**
     * 工作年限
     */
    private String serviceYear;
    /**
     * 调整前单价(元/人月)
     */
    private Double price;
    /**
     * 调整后单价(元/人月)
     */
    private Double postPrice;
    /**
     * 所属供应商CODE
     */
    private String vendorCode;
    /**
     * 所属供应商NAME
     */
    private String vendorName;
    /**
     * 项目经理CODE
     */
    private String prjMgrCode;
    /**
     * 项目经理NAME
     */
    private String prjMgrName;
    /**
     * 变更时间
     */
    private String chgDate;
    private String remarks;
    /**
     * 逻辑删除
     */
    private String isDel;

    /**
     * 状态 `0`:未调度; `1`:已调度
     */
    private String dispatched;

    /**
     * 状态 `0`:未审批通过; `1`:已审批通过
     */
    private String approvalStatus;
}