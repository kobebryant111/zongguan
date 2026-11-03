package com.work.ppm.application.dto.vendormanager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.work.ppm.application.model.vendormanager.OutsrcingAttrChg;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OutsrcingAttrChgDto extends OutsrcingAttrChg {

    private String deptName;
    private String vendorName;
    private String contractName;
    private String attachName;
    @JsonInclude
    private String projectName;
    @JsonInclude
    private String systemName;
    private String perm;
    private String permMgr;
}