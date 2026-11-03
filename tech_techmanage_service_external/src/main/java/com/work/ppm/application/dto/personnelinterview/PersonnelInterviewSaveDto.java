package com.work.ppm.application.dto.personnelinterview;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * PersonnelInterviewSaveDto
 *
 */
@Data
public class PersonnelInterviewSaveDto {

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

}
