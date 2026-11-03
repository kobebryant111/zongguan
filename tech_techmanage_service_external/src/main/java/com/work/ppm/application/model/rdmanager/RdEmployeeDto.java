package com.work.ppm.application.model.rdmanager;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget("RdEmployeeDto")
public class RdEmployeeDto {
    /**
     * 员工代码
     **/
    private Integer eid;
    /**
     * 工号
     **/
    @Excel(name = "工号", orderNum = "1")
    private String badge;
    /**
     * 所属部门代码
     **/
    private Integer depid;
    /**
     * 行内负责人
     */
    private String empName;
    /**
     * 所属部门
     */
    @Excel(name = "所属部门", orderNum = "6")
    private String chargeName;
    /**
     * 到岗日期
     */
    private String arrivalTime;
    /**
     * 离岗日期
     */
    private String leaveTime;

    /**
     * 员工状态
     **/
    private String empstatus;
    /**
     * 0.全职 1.兼职1（85%以上)、2兼职研发2(80%以上)、3兼职研发3 (50%-70%)
     */
    private String nature;
}
