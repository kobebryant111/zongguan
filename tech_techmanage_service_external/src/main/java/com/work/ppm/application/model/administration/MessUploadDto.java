package com.work.ppm.application.model.administration;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * FileUploadRequest
 *
 */
@Data
public class MessUploadDto {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 项目id
     */
    @NotBlank(message = "项目id不能为空")
    private String rdProjectId;
    /**
     * 业务类型
     */
    @NotBlank(message = "业务类型不能为空")
    private String type;
    /**
     * 项目关联人
     */
    @NotBlank(message = "项目关联人不能为空")
    private String badge;
}
