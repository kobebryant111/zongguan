package com.work.ppm.application.dto.resourceplat;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 资源新增
 */
@Data
public class ResAddedDto {
    /**
     * 唯一主键
     */
    private String id;
    /**
     * 是否删除iaas库数据
     */
    private String iaasFlag;
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
    private String amount;
    /**
     * 状态
     */
    private String status;
    /**
     * 状态变更时间
     */
    private Date statusTime;
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
     * 开始时间（查询使用）
     */
    private String startTime;
    /**
     * 结束时间（查询使用）
     */
    private String endTime;
    /**
     * 申请时间（查询使用）
     */
    private String rangeDate;
    /**
     * 预期上线时间开始时间（查询使用）
     */
    private String expectedOnlineStartTime;
    /**
     * 预期上线时间结束时间（查询使用）
     */
    private String expectedOnlineEndTime;
    /**
     * 预期上线时间（查询使用）
     */
    private String expectedOnlineTimeRangDate;
    /**
     * 资源类型
     */
    private String sourceType;
    /**
     * 所属业务部ID
     */
    private String busiDeptId;
    /**
     * 所属技术部ID
     */
    private String techDeptId;
    /**
     * 所属业务部
     */
    private String busiDeptName;
    /**
     * 所属技术部
     */
    private String techDeptName;
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
     * 新增/编辑标志
     */
    private String formFlag;
    /**
     * 流程编号
     */
    private String processInstID;
    /**
     * 压力测试报告
     */
    private String docmId;
    /**
     * 补录编号
     */
    private String applyCode;
    /**
     * 流程标题
     */
    private String flowTitle;
    /**
     * 配置子项列表
     */
    private List<ResAddedItemDto> dynamicForms;

    /**
     * 购物车
     */
    private ResAddedItemDto dynamicForm;

}
