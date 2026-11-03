package com.work.ppm.application.model.rdmanager;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目合同关联表
 *
 */
@Data
public class RdProjectContractRelation {

    /**
     * 主键
     */
    @TableId(value = "PK_ID",type = IdType.UUID)
    private String pkId;

    /**
     * 项目id
     */
    @NotBlank(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id")
    private String rdProjectId;

    /**
     * 合同id
     */
    @NotBlank(message = "合同id不能为空")
    @ApiModelProperty(value = "合同id")
    private String rdProjectContractId;

    /**
     * 占比
     */
    @ApiModelProperty(value = "占比")
    @TableField
    private BigDecimal ratio;


    /**
     * 创建人
     */
    @TableField(fill= FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill= FieldFill.INSERT)
    private Date createDate;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;

}
