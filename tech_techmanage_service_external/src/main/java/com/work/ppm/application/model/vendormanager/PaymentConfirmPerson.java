package com.work.ppm.application.model.vendormanager;

import com.work.ppm.commons.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.work.ppm.commons.annotaion.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * 支付确认-人员信息
 * </p>
 * @since 2023-01-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "PPM_PAYMENT_CONFIRM_PERSON")
public class PaymentConfirmPerson extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Long id;
    /**
     * 流程实例id
     */
    private String instanceId;
    /**
     * 工号
     */
    private String jobId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 支付年份
     */
    private String paymentYear;
    /**
     * 支付月份
     */
    private String paymentMonth;
    /**
     * 逻辑删除: 默认0 
     */
    private String isDel;
    /**
     * 单价
     */
    private Double price;
    /**
     * 应付价格
     */
    private Double actualPrice;

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

    /**
     * 应出勤天数
     */
    private String requiredAttendanceDays;
    /**
     * 实际考勤天数
     */
    private String actualAttendanceDays;

}