package com.work.ppm.application.dto.onlinemanager;

import lombok.Data;

/**
 * 上线系统类
 */
@Data
public class OnlineSubProjectInfoDto {
    /**
     * 系统编号
     */
    private String sysNo;
    /**
     * 系统名称
     */
    private String sysName;
    /**
     * 是否配合类
     */
    private boolean isAssortTest;
    /**
	 * 互联网端功能变更涉及场
	 */
    private String [] internetChangesScenes;
}
