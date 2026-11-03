package com.work.ppm.application.dto.rdmanager;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 项目创新点
 *
 */
@Data
public class RdProjectInnovaDto {

    /**
     * 主键
     */
    @NotBlank(message = "主键不为空")
    @ApiModelProperty(value = "主键")
    private String pkId;

    /**
     * 业务创新点
     */
    @Size(max = 2000)
    @ApiModelProperty(value = "业务创新点")
    private String busInnova;

    /**
     * 技术创新点
     */
    @Size(max = 2000)
    @ApiModelProperty(value = "技术创新点")
    private String techInnova;



}
