package com.work.ppm.application.dto.bankmamager;

import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import lombok.Data;

import java.util.List;

@Data
public class PpmOutSorceInterviewDto {
    /**
     * id
     */
    private Long id;
    /**
     * 入职人员证件号
     */
    private String certifNumber;
    /**
     * 流程id
     */
    private String instanceId;
    /**
     * 角色
     */
    private String msgRole;
    /**
     * 面试角色
     */
    private String msemployRole;
    /**
     * 面试官id
     */
    private String msgid;
    /**
     * 面试官工号
     */
    private String msgno;
    /**
     * 面试官工号
     */
    private String msgname;
    /**
     * 面试官部门
     */
    private String deptno;
    /**
     * 面试官部门
     */
    private String deptname;
    /**
     * 技术评分
     */
    private String technology;
    /**
     * 综合评分
     */
    private String comprehensive;
    /**
     * 项目经验
     */
    private String experience;
    /**
     * 学历
     */
    private String education;
    /**
     * 评语
     */
    private String remark;

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
