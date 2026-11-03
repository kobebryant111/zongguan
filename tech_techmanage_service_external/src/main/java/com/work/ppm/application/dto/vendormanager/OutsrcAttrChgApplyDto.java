package com.work.ppm.application.dto.vendormanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.vendormanager.OutsrcAttrChgApply;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OutsrcAttrChgApplyDto extends OutsrcAttrChgApply {
    /**
     * bps
     */
    private BpsWorkFlowDto bps;

    private List<OutsrcingAttrChgDto> outsrcingAttrChgList;
}
