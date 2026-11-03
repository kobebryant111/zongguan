package com.work.ppm.application.model.testmanager;

import lombok.Data;

@Data
public class PersonnerPortait {
    /**
     * 主键
     */
    private String pkId;
    /**
     * 工号
     */
    private String badge;
    /**
     * 姓名
     */
    private String username;
    /**
     * 所属团队
     */
    private String textTeam;
    /**
     * 所属小组
     */
    private String textGroup;
    /**
     * 成员属性
     */
    private String presenceState;
    /**
     * 供应商名称
     */
    private String outsourcname;
    /**
     *案例编写数
     */
    private String caseWritTime;
    /**
     * 执行案例总数
     */
    private String caseNumAll;
    /**
     * 冒烟执行案例数
     */
    private String smokeCaseNum;
    /**
     * SIT执行案例数
     */
    private String SITcaseNum;
    /**
     * UAT执行案例数
     */
    private String UATcaseNum;
    /**
     * 未否决缺陷数
     */
    private String notVetoedFlawNum;
    /**
     * 有效缺陷总数
     */
    private String validFlawNum;
    /**
     * 高级缺陷数
     */
    private String advancedFlawNum;
    /**
     * 中级缺陷数
     */
    private String middlleFlawNum;
    /**
     * 低级缺陷数
     */
    private String lowFlawNum;
    /**
     * 优化缺陷数
     */
    private String optimizeFlawNum;
    /**
     * 冒烟缺陷发现率
     */
    private String smokeFlawRatio;
    /**
     * SIT缺陷发现率
     */
    private String SITFlawRatio;
    /**
     * UAT缺陷发现率
     */
    private String UATFlawRatio;
    /**
     * 加班时长
     */
    private String overtimeHours;
    /**
     * 出勤天数
     */
    private String requiredAttendanceDays;
    /**
     * 报工时长
     */
    private String reportLongHours;
}
