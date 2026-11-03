package com.work.ppm.application.model.processmanager.ext;

import com.work.ppm.application.model.processmanager.PpmNetworkStick;
import lombok.Data;

/**
 * 宁管部网点设备维护扩展
 */
@Data
public class PpmNetworkStickInfo extends PpmNetworkStick {
    /**
     部门名称
     */
    private String deptName;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 状态名称
     */
    private String stateName;
    /**
     * 流程名称
     */
    private String processName;
    /**
     * 结束时间
     */
    private String endDate;

    /**
     *	发起时间字符串
     **/
    private String startDate;
    /**
     * 变更类型 名称
     */
    private String changeTypeStr;
    /**
     * 最后操作时间字符串
     */
    private String lastMofiyDateStr;
    /**
     * 变更设备名称字符串
     */
    private String changeDeviceStr;

}
