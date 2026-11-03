package com.work.ppm.application.dto.integratedmanager;

import com.work.ppm.application.model.integratedmanager.PpmIntervieweeScoreinfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 外包面试人员信息dto
 */
@Data
public class PpmIntervieweeInfoDto {
    /**
     *	id
     **/
    private Long id;
    /**
     *	面试人姓名
     **/
    private String msname;
    /**
     *	面试人性别 0:女1:男
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
     *	流程id
     **/
    private String instanceid;
    /**
     *	面试评语
     **/
    private String mspy;
    /**
     *	成绩
     **/
    private String score;
    /**
     *	面试结果 0：通过1：不通过
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
     *	面试日期
     **/
    private Date msDate;
    /**
     * 外包商公司名称
     */
    private String outsourcName;
    /**
     * 性别
     */
    private String sexName;
    /**
     * 岗位名称
     */
    private String positionName;
    /**
     * 面试人数
     */
    private Integer sumNum;
    /**
     * 通过人数
     */
    private Integer tgNum;
    /**
     * 人力需求编号
     */
    private Integer rlId;
    /**
     * 面试打分集合
     */
    private List<PpmIntervieweeScoreinfo> scoreList;
    /**
     * 人员属性（1：人力，2：项目）
     */
    private Integer researchers;

    /**
     * 部门编号
     */
    private String deptNo;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 外包商名称
     */
    private String outSourceName;
    /**
     * 花名册 证件号
     */
    private String cardNo;

    /**
     * 面试流程关联ID
     */
    private String interviewId;
    /**
     * 姓名
     */
    private String userName;
}
