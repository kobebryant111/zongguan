package com.work.ppm.application.model.rdmanager;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HrProjectYear {
    /**
     * 姓名
     */
    private String username;
    /**
     * 工号
     */
    private String badge;
    /**
     * 部门编号
     */
    private String depid;
    /**
     * 部门名称
     */
    private String chargename;
    /**
     * 代码编号
     */
    private String eidCode;
    /**
     * 月份
     */
    private String monthOfYear;
    /**
     * 出勤天数
     */
    private BigDecimal sumMonth;
}
