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
 * @since 2022-12-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_OUTSRC_ATTR_CHG_APPLY")
public class OutsrcAttrChgApply extends BaseEntity implements Serializable {

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
    private Double deptId;
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

}