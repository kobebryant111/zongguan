package com.work.ppm.application.dto.vendorcontract;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Excel(name = "工号", orderNum = "1", width = 15)
    private String jobId;
    @Excel(name = "名称", orderNum = "2")
    private String userName;
    @Excel(name = "日期", orderNum = "3")
    private String attendanceDate;
    @Excel(name = "出勤天数", orderNum = "4", type = 10)
    private String actualAttendanceDays;
    @Excel(name = "应出勤天数", orderNum = "5")
    private String requiredAttendanceDays;

}
