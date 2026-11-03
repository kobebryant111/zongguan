package com.work.ppm.application.model.vendormanager;

import com.work.ppm.commons.annotaion.Id;
import com.work.ppm.commons.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 附件管理
 * </p>
 * @since 2022-10-26
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_ATTATCH_MANAGEMENT")
public class AttatchManagement extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 文件编号; 格式：yyyyMMdd001
     */
    private String fileNo;

    /**
     * 文件名称
     */
    @NotBlank
    @Length(max=50,message = "附件名称格式不符")
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
    @NotBlank
    @Length(max=220,message = "超过文档上限")
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
    @Digits(integer = 5, fraction = 1)
    private Integer totals;

    /**
     * 可用人数
     */
    @Digits(integer = 5, fraction = 1)
    private Integer availableCount;

    /**
     * 已用人数
     */
    @Digits(integer = 5, fraction = 1)
    private Integer numberUsed;

    private Long contractInfo;
}