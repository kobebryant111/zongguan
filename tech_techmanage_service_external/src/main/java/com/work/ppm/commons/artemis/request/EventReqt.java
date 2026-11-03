package com.work.ppm.commons.artemis.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventReqt {
    // 事件类型
    private List<Integer> eventTypes;
    // 指定事件接收的地址，采用restful回调模式，支持http和https，
    // 样式如下：http://ip:port/eventRcv或者 https://ip:port/eventRcv 不超过1024个字符，
    // 事件接收地址由应用方负责按指定的规范提供，报文格式如附录D.
    // 3事件报文格式，事件接收接口不需要认证
    private String eventDest;
    // 订阅类型 0：订阅原始事件 1：联动事件 2：原始事件和联动事件，不填使用默认值0
    private Integer subType;
    // 事件等级，订阅类型为0时，此参数无效，
    // 使用默认值0 在订阅类型为1时，不填使用默认值[1,2,3] 在订阅类型为2时，
    // 不填使用默认值[0,1,2,3] 数组大小不超过32，事件等级大小不超过31
    private List<Integer> eventLvl;
}
