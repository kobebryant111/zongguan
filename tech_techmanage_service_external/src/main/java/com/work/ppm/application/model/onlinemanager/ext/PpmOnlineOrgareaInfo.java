package com.work.ppm.application.model.onlinemanager.ext;

import com.work.ppm.application.model.onlinemanager.PpmOnlineOrgarea;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class PpmOnlineOrgareaInfo extends PpmOnlineOrgarea {
    /**
     * 系统编号
     */
    private String sysNo;
    /**
     * 上线流程名称
     */
    private String projectname;
}