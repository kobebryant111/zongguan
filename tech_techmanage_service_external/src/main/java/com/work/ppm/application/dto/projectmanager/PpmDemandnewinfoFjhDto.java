package com.work.ppm.application.dto.projectmanager;

import lombok.Data;

/**
 * 奋进号业务意向
 * 回传给奋进号数据
 *
 */
@Data
public class PpmDemandnewinfoFjhDto {
    /**
     * 奋进号意向编号
     */
    private String issueId;
    /**
     * 奋进号状态结果 ok：正常，0：撤回
     */
    private String approvalResult;
    /**
     * 系统负责人工号
     */
    private String technologyManager;
    /**
     * 科技工作量
     */
    private Double technologyWorkload;
    /**
     *  业务工作量
     */
    private Double technologyBudget;
    /**
     * 业务
     */
    private Double businessBudget;
    /**
     * 总工做量
     */
    private Double sumWorkload;
    /**
     * 项目规模
     */
    private String projectScale;
    /**
     * 是否大中型 02是  01否
     */
    private String isLarge;
}
