package com.work.ppm.commons.artemis;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Artemis通用响应对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtemisResponse {
    /**
     * 返回编码
     */
    private String code;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 结果
     */
    private JSONObject data;
}
