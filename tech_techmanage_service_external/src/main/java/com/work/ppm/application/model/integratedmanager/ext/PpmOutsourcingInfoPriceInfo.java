package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmOutsourcingInfoPrice;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class PpmOutsourcingInfoPriceInfo extends PpmOutsourcingInfoPrice {
    /**
     * 外包商名称
     */
    private String outsourcname;
    /**
     * 人员类型
     *
     */
    private String positionName;

    /**
     *	删除变量
     **/
    private String delVal;

}
