package com.work.ppm.commons.artemis.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Artemis通用响应对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlResp {
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
    private List<ControlResult> data;

    @Data
    public static class ControlResult {
        // 门禁点唯一标识
        private String doorIndexCode;
        // 0标识反控成功，其他表示失败
        private Integer controlResultCode;
        // 与controlResultCode对应的描述，
        private String controlResultDesc;
    }
}
