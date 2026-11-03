package com.work.ppm.application.dto.personnelinterview;

import lombok.Data;

/**
 * PersonnelInterviewPageDto
 *
 */
@Data
public class PersonnelInterviewPageDto {

    /**
     * 序号
     */
    private Integer seq;

    /**
     * 约谈ID
     */
    private String personnelInterviewId;

    /**
     * 标题
     */
    private String title;

    /**
     * 约谈照片ID
     */
    private String picId;

    /**
     * 约谈内容ID
     */
    private String wordId;

}
