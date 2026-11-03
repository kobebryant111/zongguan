package com.work.ppm.commons.artemis.request;

import com.work.ppm.commons.artemis.enums.ControlTypeEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 门禁点反控请求参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlReqt {

    /**
     * 门禁点唯一标识，最大支持10个门禁点。
     */
    List<String> doorIndexCodes;

    /**
     * 监控类型,0-常开 1-门闭 2-门开 3-常闭
     */
    ControlTypeEnums controlType;
}
