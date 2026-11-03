package com.work.ppm.commons.lang.queue;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.Data;

import java.util.Map;

/**
 * 实体类
 */
@Data
public class MobileHttpQueue {
    /**
     * 地址
     */
    private String url;
    /**
     * 参数
     */
    private String params;
    /**
     * token
     */
    private String token;
    /**
     * 流程工作id
     */
    private String workitemId;
    /**
     * 流程实例id
     */
    private String processInstId;
    /**
     * 密钥
     */
    private String secertKey;

    /**
     * 有参构造
     */
    public MobileHttpQueue(String url,String params, String token, String secertKey) {
        this.url = url;
        this.params = params;
        this.token = token;
        this.secertKey = secertKey;
        Map map = JsonUtil.jsonToMap(params);
        String bpsStr = map.get("bps") + "";
        BpsWorkFlowDto bps = JsonUtil.jsonToClass(bpsStr, BpsWorkFlowDto.class);
        this.processInstId = bps.getProcessInstId();
        this.workitemId = bps.getWorkItemId();
    }
}
