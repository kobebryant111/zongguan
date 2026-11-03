package com.work.ppm.application.model.rdmanager;

import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget("CommArrivarEmployDto")
public class CommArrivarEmployDto {
    /**
     * 到岗日期
     */
    private String arrivalTime;
    /**
     * 员工列表
     */
    private List<CommRdEmployeeDto> selectedData;
}
