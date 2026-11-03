package com.work.ppm.application.model.depkpi.ext;

import com.work.ppm.application.model.depkpi.PpmDepkpiTarget;
import lombok.Data;


/**
 * 指标
 *
 */
@Data
public class PpmDepkpiTargetInfo extends PpmDepkpiTarget {
    /**
     * 考核制定人
     **/
    private String marker;
}
