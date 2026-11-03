package com.work.ppm.application.model.depkpi.ext;

import com.work.ppm.application.model.depkpi.PpmDepkpiModel;
import lombok.Data;

/**
 * 模板信息表
 *
 */
@Data
public class PpmDepkpiModelInfo extends PpmDepkpiModel {
    /**
     * 已配置基准分
     **/
    private Long basepoint;
}
