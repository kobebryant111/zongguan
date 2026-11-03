package com.work.ppm.application.dto.integratedmanager;

import lombok.Data;

import java.util.Date;

/**
 * 面试归档dto
 */
@Data
public class PpmIntervieweeOverinfoDto {
    /**
     *	工作年限
     **/
    private String workyears;
    /**
     *	建议等级
     **/
    private String suggestlevel;
    /**
     *	外包商id
     **/
    private String wbsid;
    /**
     *	岗位
     **/
    private String position;
    /**
     *	简历
     **/
    private String jl;
    /**
     *	面试评语
     **/
    private String mspy;
    /**
     *	成绩
     **/
    private String score;
    /**
     *	面试结果 0：不通过1：通过
     **/
    private String interviewresult;
    /**
     *	建议单价
     **/
    private String suggestprice;
    /**
     *	修改理由
     **/
    private String reason;
    /**
     *	流程id
     **/
    private String instanceid;
    /**
     *	创建日期
     **/
    private String createtime;
    /**
     *	是否准入 0：否1：是
     **/
    private String isrz;
    /**
     *	字段名
     **/
    private Date msDate;
    /**
     *	id
     **/
    private Long id;
    /**
     *	面试人员信息
     **/
    private String msname;
    /**
     *	性别0：女1：男
     **/
    private String sex;
    /**
     *	身份证
     **/
    private String idcard;
    /**
     *	出生日期
     **/
    private String birthday;
    /**
     * 人员属性（1：人力，2：项目）
     */
    private Integer researchers;
}
