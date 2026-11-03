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
 * 人员属性变更
 * </p>
 * @since 2022-12-07
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_OUTSRCING_ATTR_CHG")
public class OutsrcingAttrChg extends BaseEntity implements Serializable {

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
     * 用户编号
     */
    private String userCode;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 所属部门id
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
     * 预计单价(元/人月)
     */
    private Double price;
    /**
     * 所属供应商
     */
    private String vendorCode;
    /**
     * 项目经理code
     */
    private String prjMgrCode;
    /**
     * 项目经理name
     */
    private String prjMgrName;
    /**
     * 变更时间
     */
    private String chgDate;
    /**
     * 合同id
     */
    private Long contractId;
    /**
     * 总办议案id
     */
    private Long attachId;
    private String remarks;
    /**
     * 逻辑删除
     */
    private String isDel;

    /**
     * 外包属性 `1`:人力; `0`:项目
     */
    private String outsourceType;

    /**
     * 状态 `0`:未调度; `1`:已调度
     */
    private String dispatched;

    /**
     * 状态 `0`:未审批通过; `1`:已审批通过
     */
    private String approvalStatus;

    /**
     * 项目id
     */
    private String  projectId;

    /**
     * 系统id
     */
    private String systemId;
    /**
     * 系统not id
     */
    private String instanceNotId;

}