package com.work.ppm.application.model.depkpi.ext;

import com.work.ppm.application.model.depkpi.PpmDepkpiDepmodelDetailTmp;
import lombok.Data;
/**
 * 模板详情映射表
 */
@Data
public class PpmDepkpiDepmodelDetailTmpInfo extends PpmDepkpiDepmodelDetailTmp {
    /**
     *	指标名
     **/
    private String targetname;
}
