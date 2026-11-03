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
 * 外包人员考核
 * </p>
 * @since 2023-01-03
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_OUTSRC_EMP_ASSESS")
public class OutsrcEmpAssess extends BaseEntity implements Serializable {

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
     * 所属部门ID
     */
    private String deptId;
    /**
     * 人员code
     */
    private String userCode;
    /**
     * 人员名称
     */
    private String userName;
    /**
     * 项目经理code
     */
    private String prjMgrCode;
    /**
     * 项目经理name
     */
    private String prjMgrName;
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
     * 所属供应商
     */
    private String vendorCode;
    /**
     * 类型
     */
    private String position;
    /**
     * 考核等级
     */
    private String assessLevel;
    /**
     * 平均分
     */
    private String avgScore;
    /**
     * 综合评价
     */
    private String evaluation;
    /**
     * 逻辑删除
     */
    private String isDel;

}