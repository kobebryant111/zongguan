package com.work.ppm.application.dto.integratedmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.util.Date;


/**
 * 闸机权限管理 Excel对象
 *
 */
@Data
@ExcelTarget("ppmZjAuthorityExcelDto")
public class PpmZjAuthorityExcelDto {
    /**
     * 工号
     **/
    @Excel(name = "工号", orderNum = "1")
    private String badge;

    /**
     * 姓名
     **/
    @Excel(name = "姓名", orderNum = "2")
    private String empname;

    /**
     * 部门
     **/
    @Excel(name = "部门", orderNum = "3")
    private String depname;
    /**
     * 状态
     **/
    @Excel(name = "状态", orderNum = "4")
    private String empstatus;
    /**
     * 楼层
     **/
    @Excel(name = "楼层", orderNum = "5")
    private String zjname;
    /**
     * 开始时间
     **/
    @Excel(name = "开始时间", orderNum = "6", format = "yyyy-MM-dd")
    private Date begindate;
    /**
     * 结束时间
     **/
    @Excel(name = "结束时间", orderNum = "7", format = "yyyy-MM-dd")
    private Date enddate;
}
