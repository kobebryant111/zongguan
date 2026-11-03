package com.work.ppm.application.model.resourceplat;

import lombok.Data;

import java.util.List;

/**
 * iaas资源查询返回对象
 */
@Data
public class IaasResourceVo {
    /**
     * 唯一主键
     */
    private String id;
    /**
     * 价格申请单号
     */
    private String priceReqNo;
    /**
     * 应用ID
     */
    private String appNo;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 项目ID
     */
    private String projectNo;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 补录流程编号
     */
    private String reNewProcessNo;
    /**
     * 预计上线时间
     */
    private String expectedOnlineTime;
    /**
     * CMDB是否已经使用
     */
    private String isCmdbUsed;
    /**
     * 总价（元）
     */
    private float amount;
    /**
     * 状态
     */
    private String status;
    /**
     * 状态变更时间
     */
    private String statusTime;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建人
     */
    private String creatorName;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 0资源新增1资源扩充
     */
    private String sourceType;
    /**
     * 所属业务部
     */
    private String busiDeptName;
    /**
     * 所属业务部ID
     */
    private String busiDeptId;
    /**
     * 所属技术部
     */
    private String techDeptName;
    /**
     * 所属业务部ID
     */
    private String techDeptId;
    /**
     * 系统运维等级
     */
    private String sysOperLevel;
    /**
     * 是否涉及资金交易
     */
    private String isFundTrans;
    /**
     * 预算编号，综合部审批时填写
     */
    private String budgetNo;
    /**
     * CMDB系统唯一识别编号
     */
    private String ciid;
    /**
     * 流程实例ID
     */
    private String processInstID;

    /**
     * item与ext的组合返回对象
     */
    List<AddedItemExtVo> itemExtVoList;

}
