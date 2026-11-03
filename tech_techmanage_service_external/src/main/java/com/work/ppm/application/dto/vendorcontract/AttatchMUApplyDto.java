package com.work.ppm.application.dto.vendorcontract;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.vendormanager.AttatchMUApply;
import com.work.ppm.application.model.vendormanager.Doc;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AttatchMUApplyDto extends AttatchMUApply {
    /**
     * 附件文档
     */
    List<Doc> docList;
    /**
     * bps
     */
    private BpsWorkFlowDto bps;

}
