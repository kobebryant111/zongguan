package com.work.ppm.application.model.projectmanager.ext;

import com.work.ppm.application.model.projectmanager.PpmDemandnewinfo;
import lombok.Data;

/**
 * 扩展类
 */
@Data
public class PpmDemandnewinfoInfo extends PpmDemandnewinfo {
    /**
     * 业务项目经理名称
     */
    private String busiUserName;
    /**
     * 牵头业务部门名称
     */
    private String demandDeptName;
    /**
     * 系统科技主管部门名称
     */
    private String tectDeptName;
    /**
     * 系统名称
     */
    private String sysName;
    /**
     * 项目状态
     */
    private String projectStatus;
    /**
     * 项目编号
     */
    private String projectNo;
    /**
     * 期望上线日期
     */
    private String expectdate;
    /**
     * 意向发起人名称
     */
    private String demandUserName;
    /**
     * 系统业务主管部门
     */
    private String busiDeptName;
    /**
     * 产品经理
     */
    private String prodeuctManagerUserName;

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
