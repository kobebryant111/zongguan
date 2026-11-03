package com.work.ppm.application.model.interpoints;

import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget("CommInterPointsDto")
public class CommInterPointsDto {
    /**
     * 主键
     */
    private String pkId;
    /**
     * 项目规模
     */
    private String projectScale;
    /**
     * 项目规模名称
     */
    private String projectScaleName;
    /**
     * 面试角色
     */
    private String viewRole;
    /**
     * 面试角色名称
     */
    private String viewRoleName;
    /**
     * 考察项
     */
    private String researchNape;
    /**
     * 考察项名称
     */
    private String researchNapeName;
    /**
     * A
     */
    private int scoreA;
    /**
     * B
     */
    private int scoreB;
    /**
     * C
     */
    private int scoreC;
    /**
     * D
     */
    private int scoreD;
}
