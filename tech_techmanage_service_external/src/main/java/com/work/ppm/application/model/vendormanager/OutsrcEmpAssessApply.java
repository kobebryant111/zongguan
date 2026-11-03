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
 * 人员考核
 * </p>
 * @since 2023-01-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_OUTSRC_EMP_ASSESS_APPLY")
public class OutsrcEmpAssessApply extends BaseEntity implements Serializable {

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
     * 部门id
     */
    private String deptId;
    /**
     * 考核人数
     */
    private Integer totals;
    /**
     * 审批状态 0:未通过; 1:通过
     */
    private String approvalStatus;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 申请人
     */
    private String applier;
    /**
     * 逻辑删除
     */
    private String isDel;

    /**
     * 考核类型
     */
    private String assessType;
    /**
     * 考核年份
     */
    private String assessYear;
    /**
     * 考核季度
     */
    private String assessAnnual;
    /**
     * 项目经理名称
     */
    private String prjMgrName;

}