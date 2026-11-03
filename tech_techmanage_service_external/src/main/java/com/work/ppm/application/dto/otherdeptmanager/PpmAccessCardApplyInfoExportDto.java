package com.work.ppm.application.dto.otherdeptmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;


/**
 *  监保部台账门禁卡流程详情 导出对象
 */
@Data
public class PpmAccessCardApplyInfoExportDto {

    /**
     *	门禁卡编号
     **/
    @Excel(name = "门禁卡编号", orderNum = "1")
    private String accessCardNumber;
    /**
     * 员工所属 外包公司
     */
    @Excel(name = "员工所属 外包公司", orderNum = "2")
    private String outsourceName;
    /**
     *	领用日期
     **/
    @Excel(name = "领用日期", orderNum = "3", exportFormat = "yyyy-MM-dd")
    private Date receiveTime;

    /**
     * 领用人签字
     */
    @Excel(name = "领用人签字", orderNum = "4")
    private String userName;

    /**
     * 陪同人签字
     */
    @Excel(name = "陪同人签字", orderNum = "5")
    private String emptyTwo;



}
