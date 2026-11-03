package com.work.ppm.application.dto.vendormanager;


import com.work.ppm.application.model.vendormanager.OutsrcEmpAssess;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class OutsrcEmpAssessDto extends OutsrcEmpAssess {
    private String deptName;
    private String vendorName;
    private String oprMode = "manual"; // 用以区分定时调度和手工发起
}