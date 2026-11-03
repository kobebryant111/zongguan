package com.work.ppm.application.dto.processmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;


/**
 * 新支行交付流程Excel
 *
 */
@Data
public class PpmNewBranchBankAcceptanceExcelDto {
    /**
     * 流程标题
     **/
    @Excel(name = "流程标题", orderNum = "1")
    private String processTitle;
    /**
     * 流程编号
     **/
    @Excel(name = "流程编号", orderNum = "2")
    private String instanceId;
    /**
     * 状态 1:进行中，2：已完成，3：已删除
     **/
    @Excel(name = "状态", orderNum = "3")
    private String state;
    /**
     * 创建时间
     **/
    private Date createDate;
    /**
     * 创建时间string
     **/
    @Excel(name = "发起时间", orderNum = "4")
    private String createDateStr;
    /**
     * 最后操作时间
     **/
    private Date lastMofiyDate;
    /**
     * 最后操作时间string
     **/
    @Excel(name = "最后操作时间", orderNum = "5")
    private String lastMofiyDateStr;
    /**
     * 创建人
     **/
    @Excel(name = "创建人", orderNum = "6")
    private String userName;
    /**
     * 当前处理人
     **/
    @Excel(name = "当前处理人", orderNum = "7")
    private String currentId;
    /**
     * 流程说明
     **/
    @Excel(name = "流程说明", orderNum = "8")
    private String processDescribe;
    /**
     * Netcool监控责任人
     **/
    @Excel(name = "Netcool监控责任人", orderNum = "9")
    private String netcoolManagerId;
    /**
     * Netcool监控是否上线(0-否，1-是)
     **/
    @Excel(name = "Netcool监控是否上线", orderNum = "10")
    private String netcoolDecision;
    /**
     * UPS监控责任人
     **/
    @Excel(name = "UPS监控责任人", orderNum = "11")
    private String upsManagerId;
    /**
     * UPS监控是否上线(0-否，1-是)
     **/
    @Excel(name = "UPS监控是否上线", orderNum = "12")
    private String upsDecision;
    /**
     * ACK策略责任人
     **/
    @Excel(name = "ACK策略责任人", orderNum = "13")
    private String ackManagerId;
    /**
     * ACK策略是否上线(0-否，1-是)
     **/
    @Excel(name = "ACK策略是否上线", orderNum = "14")
    private String ackDecision;

}
