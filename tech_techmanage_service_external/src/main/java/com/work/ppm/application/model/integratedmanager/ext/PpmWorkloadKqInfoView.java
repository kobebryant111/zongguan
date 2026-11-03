package com.work.ppm.application.model.integratedmanager.ext;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.work.ppm.application.model.integratedmanager.PpmSupplierOutsourcing;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 工作量明细 实体对象
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PpmWorkloadKqInfoView extends PpmSupplierOutsourcing {

    /**
     * 供应商名称
     **/
    @Excel(name = "供应商名称", orderNum = "0")
    private String outsourcName;

    /**
     * 合同名称
     **/
    @Excel(name = "对应合同名称", orderNum = "1")
    private String contractName;

    /**
     * 实际出勤天数
     **/
    @Excel(name = "出勤天数", orderNum = "5")
    private Double actualAttendanceDays;
    /**
     * 当月应出勤天数
     **/
    @Excel(name = "工作日天数", orderNum = "6")
    private Double requiredAttendanceDays;
    /**
     * 累计工作人月
     **/
    @Excel(name = "累计工作人月", orderNum = "7")
    private Double cumulativeWorkingMonth;
    /**
     * 累计加班时长 /h OVERTIME_HOURS
     **/
    @Excel(name = "累计加班时长", orderNum = "8")
    private Double overtimeHours;
    /**
     * 累计请假天数
     **/
    @Excel(name = "累计请假天数", orderNum = "9")
    private Double cumulativeLeaveDays;
    /**
     * 累计调休天数
     **/
    @Excel(name = "累计调休天数", orderNum = "10")
    private Double cumulativeOffDays;
    /**
     * 忘记打卡次数
     **/
    @Excel(name = "忘记打卡次数", orderNum = "11")
    private Long forgetClockInNumber;
    /**
     * 迟到次数
     */
    @Excel(name = "迟到次数", orderNum = "12")
    private Long beLateNumber;
    /**
     * 早退次数
     */
    @Excel(name = "早退次数", orderNum = "13")
    private Long leaveEarlyNumber;

    /**
     * 异常扣减天数
     */
    @Excel(name = "异常扣减天数", orderNum = "14")
    private Double exceptionalDeductionNumber;

    /**
     * 其他次数
     */
    @Excel(name = "其他次数", orderNum = "15")
    private Long otherNumber;
    /**
     * 在场时段
     **/
    @Excel(name = "在场时段", orderNum = "16")
    private String onSitePeriod;
    /**
     * 开始日期
     **/
    private Date intersectStart;
    /**
     * 结束日期
     **/
    private Date intersectEnd;

}
