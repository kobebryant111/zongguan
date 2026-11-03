package com.work.ppm.application.dto.vendormanager;

import com.work.ppm.application.model.vendormanager.OutsrcingPriceChg;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OutsrcingPriceChgDto extends OutsrcingPriceChg {
    private String deptName;
    private String perm;
    private String permMgr;
}
