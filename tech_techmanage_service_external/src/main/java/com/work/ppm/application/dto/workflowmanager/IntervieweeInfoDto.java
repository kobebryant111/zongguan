package com.work.ppm.application.dto.workflowmanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.ppm.application.dto.workflowmanager.ext.ReadyParamsDto;
import lombok.Data;

@Data
@JsonInclude
public class IntervieweeInfoDto extends ReadyParamsDto {

    /**
     * 编号
     */
    String id;
    /**
     * 姓名
     */
    String msname;
    /**
     * 性别
     */
    String sexName;
    /**
     * 出生日期
     */
    String birthday;
    /**
     * 外包商id
     */
    String wbsid;
    /**
     * 外包商名称
     */
    String outsourcName;
    /**
     * 人员类型编码
     */
    String position;
    /**
     * 人员类型名称
     */
    String positionName;
    /**
     * 人员属性
     */
    String researchers;
    /**
     * 人员属性名称
     */
    String researchersName;
    /**
     * 评语
     */
    String mspy;
    /**
     * 年限
     */
    String workyears;
    /**
     * 等级
     */
    String suggestlevel;
    /**
     * 成绩
     */
    String score;
    /**
     * 面试结果
     */
    String interviewresult;
    /**
     * 面试结果
     */
    String interviewresultName;
    /**
     * 建议单价
     */
    String suggestprice;
    /**
     * 修改理由
     */
    String reason;

}
