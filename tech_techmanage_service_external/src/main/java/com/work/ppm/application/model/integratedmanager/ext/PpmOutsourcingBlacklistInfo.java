package com.work.ppm.application.model.integratedmanager.ext;

import com.work.ppm.application.model.integratedmanager.PpmOutsourcingBlacklist;
import lombok.Data;

/**
 * 外包黑名单扩展
 */
@Data
public class PpmOutsourcingBlacklistInfo extends PpmOutsourcingBlacklist {
    /**
     *	二级部部门名称
     **/
    private String depName;
    /**
     *	供应商名称
     **/
    private String supplier;
}
