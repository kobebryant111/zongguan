package com.work.ppm.application.model.bankmanager;

import lombok.Data;

@Data
public class PpmOutSourceInterview {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 入职人员证件号
     */
    private String certifNumber;
    /**
     * 角色
     */
    private String msgRole;
    /**
     * 面试官id
     */
    private String msgId;
    /**
     * 流程id
     */
    private String flowPathId;
    /**
     * 面试官工号
     */
    private String interCode;
    /**
     * 面试官部门
     */
    private String interdepCode;
    /**
     * 技术评分
     */
    private String technoLogy;
    /**
     * 综合评分
     */
    private String comPrehenSive;
    /**
     * 项目经验
     */
    private String experIence;
    /**
     * 学历
     */
    private String education;
    /**
     * 评语
     */
    private String remark;

    /**
     * 面试角色
     */
    private String msemployRole ;

    /**
     * 通用工具使用
     */
    private String universalToolUse ;

    /**
     * 项目管理知识
     */
    private String projectManageKnowledge ;

    /**
     * 沟通表达
     */
    private String commExpression ;

    /**
     * 项目经验
     */
    private String projectExperience ;

    /**
     * 项目规模-项目经理
     */
    private String projectScaleXmjl ;

    /**
     * 项目规模-测试经理
     */
    private String projectScaleJcjl ;

    /**
     * 项目规模
     */
    private String projectScale ;

    /**
     * 评分结果
     */
    private String scoreResult ;

    /**
     * 代码研发
     */
    private String codeDevelopment ;

    /**
     * 架构设计
     */
    private String architectureDesign ;

    /**
     * 专业知识
     */
    private String profesKnowledge ;

    /**
     * 驻场情况
     */
    private String standCondition ;

    /**
     * 产品能力
     */
    private String productAbility ;

}
