package com.work.ppm.commons.constant;

import java.util.Map;
import java.util.Set;

/**
 * 读取工作流消息配置属性
 */
public class WorkflowSendMsg {
    /**
     * getProcessorWechatApprovalConfig
     * @return Set
     */
    public static Set<String> getProcessorWechatApprovalConfig() {
        return WechatApprovalProperties.getConfig();
    }

    /**
     * getWechatSendConfig
     * @return map
     */
    public static Map<String, String> getWechatSendConfig() {
        return WechatSendProperties.getConfig();
    }

    /**
     * getProcessorMobileOAApprovalConfig
     * @return set
     */
    public static Set<String> getProcessorMobileOAApprovalConfig() {
        return MobileOAApprovalProperties.getConfig();
    }
}


