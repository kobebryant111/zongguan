package com.work.ppm.application.model.vendormanager;

import com.work.ppm.commons.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.work.ppm.commons.annotaion.Id;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * 附件管理更新申请
 * </p>
 * @since 2022-11-02
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_ATTATCH_M_U_APPLY")
public class AttatchMUApply extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Long id;
    /**
     * 附件管理-记录id
     */
    private Long recordId;

    private Long contractInfo;
    private String instanceId;
    private String flowId;
    /**
     * 文件编号; 格式：yyyyMMdd001
     */
    private String fileNo;
    /**
     * 文件名称
     */
    @Column(name = "ATTACH_NAME")
    private String fileName;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 生效日期
     */
    private String startDate;
    /**
     * 失效日期
     */
    private String endDate;
    /**
     * 文件状态
     */
    private String status;
    /**
     * 文档
     */
    private String doc;
    /**
     * 逻辑删除
     */
    private String isDel;
    /**
     * 是否关联, Y/N
     */
    private String isAssociated;
    /**
     * 总人数
     */
    private Integer totals;
    /**
     * 可用人数
     */
    private Integer availableCount;
    /**
     * 已用人数
     */
    private Integer numberUsed;

}