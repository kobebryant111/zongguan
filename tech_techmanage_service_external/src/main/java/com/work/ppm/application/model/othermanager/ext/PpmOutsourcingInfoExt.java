package com.work.ppm.application.model.othermanager.ext;

import com.work.ppm.application.model.othermanager.PpmOutsourcingInfo;
import lombok.Data;


/**
 * 描述
 *
 */
@Data
public class PpmOutsourcingInfoExt extends PpmOutsourcingInfo {

    /**
     * 供应商编号查询
     */
    private String orgCodeVal;
}
