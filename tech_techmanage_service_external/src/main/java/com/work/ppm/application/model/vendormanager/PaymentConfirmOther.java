package com.work.ppm.application.model.vendormanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 费用支付确认
 * </p>
 * @since 2022-11-06
 */

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentConfirmOther implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    @Excel(name = "结算年份", orderNum = "3")
    private String paymentYear;
    @Excel(name = "结算月份", orderNum = "4")
    private String paymentMonth;
    @Excel(name = "工号", orderNum = "1")
    private String jobId;
    @Excel(name = "姓名", orderNum = "2")
    private String userName;
    @Excel(name = "单价", orderNum = "5")
    private Double price;
    @Excel(name = "结算金额", orderNum = "9", type=10,isStatistics=true)
    private Double actualPrice;
    @Excel(name = "应出勤", orderNum = "7")
    private String requiredAttendanceDays;
    @Excel(name = "实际有效出勤", orderNum = "8")
    private String actualAttendanceDays;
    /**
     * 范围内天数
     */
    private Double requiredAttendanceDayst;
    /**
     * 月份内天数
     */
    private Double requiredAttendanceDaysq;
    /**
     * 范围内天数
     */
    private Double actualAttendanceDayst;
    /**
     * 月份内天数
     */
    private Double actualAttendanceDaysq;


}