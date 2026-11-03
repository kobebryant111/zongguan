package com.work.ppm.application.model.flowmanager.ext;

import com.work.ppm.application.model.flowmanager.PpmFlowGrant;
import lombok.Data;

/**
 * 扩展类
 */
@Data
public class PpmFlowGrantInfo  extends PpmFlowGrant {
    /**
     * 时间
     */
    private String date;
}
