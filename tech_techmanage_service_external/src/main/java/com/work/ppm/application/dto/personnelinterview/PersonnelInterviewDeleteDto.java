package com.work.ppm.application.dto.personnelinterview;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * PersonnelInterviewFileUploadDto
 *
 */
@Data
public class PersonnelInterviewDeleteDto {

    /**
     * 约谈id
     */
    @NotNull(message = "约谈id不能为空")
    private List<String> personnelInterviewIdList;
}
