package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmInterviewApply;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class PpmInterviewApplyInfo extends PpmInterviewApply {
    /**
     * 面试人名称
     */
    private String msname;
    /**
     * 身份证
     */
    private String idcard;
    /**
     * 面试结果 0：通过1：不通过
     */
    private String interviewresult;
    /**
     * 性别
     */
    private String sex;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 建议等级
     */
    private String suggestlevel;
    /**
     * 工作年限
     */
    private String workyears;
    /**
     * 岗位
     */
    private String position;
    /**
     * 建议单价
     */
    private String suggestprice;
    /**
     * 是否入职
     */
    private String isrz;
    /**
     * 简历
     */
    private String jl;
    /**
     * 成绩
     */
    private Double score;
    /**
     * 外包商编号
     */
    private String wbsid;
    /**
     * 外包商名称
     */
    private String outsourcname;
    /**
     * 人员属性
     */
    private String researchers;
}
