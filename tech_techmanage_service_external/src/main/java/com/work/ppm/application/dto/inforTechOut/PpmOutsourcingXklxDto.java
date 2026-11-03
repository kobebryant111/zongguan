package com.work.ppm.application.dto.inforTechOut;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;

@Data
@ExcelTarget("PpmOutsourcingXklxDto")
public class PpmOutsourcingXklxDto {
    /**
     * 外包需求编号
     */
    @Excel(name = "外包需求编号", width = 25 ,orderNum = "1")
    private String outReqNum;
    /**
     * 外包需求名称
     */
    @Excel(name = "外包需求名称", width = 25 ,orderNum = "2")
    private String outreqName;

    /**
     * 外包类别
     */
    @Excel(name = "外包类别", width = 25 ,orderNum = "3",replace =
            {"咨询规划类_zxgf","开发测试类_kfcs","运行维护类_ywwh","安全服务类_aqfw","业务支持类_ywzc"})
    private String outType;

    /**
     * 申请人名称
     */
    @Excel(name = "申请人",width = 25 ,orderNum = "4")
    private String empname;

    /**
     * 申请部门名称
     */
    @Excel(name = "申请部门",width = 45 , orderNum = "5")
    private String alctionLocation;

    /**
     * 申请时间
     */
    @Excel(name = "申请时间", width = 25 ,orderNum = "6", exportFormat = "yyyy-MM-dd")
    private Date alctionTime;

    /**
     * 外包用工部门
     */
    @Excel(name = "外包用工部门",width = 45 , orderNum = "7")
    private String outLocation;

    /**
     * 审批状态
     */
    @Excel(name = "审批状态", width = 20 ,orderNum = "8",replace =
            {"草稿_-1","退回申请人_0","审批中_1","审批完成_2"})
    private Integer flag;
}
