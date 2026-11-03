package com.work.ppm.application.dto.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.vendormanager.OutsrcEmpAssessApply;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OutsrcEmpAssessApplyDto extends OutsrcEmpAssessApply {

    private BpsWorkFlowDto bps;
    /**
     * 考核人员列表
     */
    private List<OutsrcEmpAssessDto> outsrcEmpAssessList;
    private String perm;
    private String permMgr;

}