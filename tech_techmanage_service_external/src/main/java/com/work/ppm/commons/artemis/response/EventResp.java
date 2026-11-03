package com.work.ppm.commons.artemis.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * 事件订阅响应对象
 */
@lombok.Data
public class EventResp {
    // 通知方法名称 默认事件类通知名称为”OnEventNotify”
    private String method;
    // 通知方法参数
    private Params params;

    /**
     * 通知方法参数
     */
    @lombok.Data
    public static class Params {
        // 事件分类
        private String ability;
        // 事件消息
        private List<Event> events;
        // 事件发送时间
        private String sendTime;

    }

    /**
     * 事件消息
     */
    @lombok.Data
    public static class Event {
        // 事件详情
        private Data data;
        // 事件的唯一id
        private String eventId;
        // 事件类型码
        private Integer eventType;
        // 事件产生时间
        private Date happenTime;
        // 子类indexcode
        private String srcIndex;
        // 设备名称
        private String srcName;
        // 资源indexcode
        private String srcParentIndex;
        // 资源类型
        private String srcType;
        // 事件状态0-瞬时1-开始2-停止3-事件脉冲4-事件联动结果更新5-异步图片上传
        private Integer status;
        // 脉冲超时时间
        private Integer timeout;
    }

    /**
     * 事件详情
     */
    @lombok.Data
    public static class Data {
        // 人员身份证信息
        @JsonProperty("ExtEventIdentityCardInfo")
        private EventIdentityCardInfo extEventIdentityCardInfo;
        // 人员通道号
        @JsonProperty("ExtAccessChannel")
        private Integer extAccessChannel;
        // 报警输入/防区通道
        @JsonProperty("ExtEventAlarmInID")
        private Integer ExtEventAlarmInID;
        // 报警输出通道
        @JsonProperty("ExtEventAlarmOutID")
        private Integer extEventAlarmOutID;
        // 卡号
        @JsonProperty("ExtEventCardNo")
        private String extEventCardNo;
        // 事件输入通道
        @JsonProperty("ExtEventCaseID")
        private Integer extEventCaseID;
        // 事件类型代码
        @JsonProperty("ExtEventCode")
        private Integer extEventCode;
        // 通道事件信息
        @JsonProperty("ExtEventCustomerNumInfo")
        private EventCustomerNumInfo extEventCustomerNumInfo;
        // 门编号
        @JsonProperty("ExtEventDoorID")
        private Integer extEventDoorID;
        // 身份证图片URL
        @JsonProperty("ExtEventIDCardPictureURL")
        private String extEventIDCardPictureURL;
        // 进出方向 进出类型<br>1：进<br>0：出<br>-1:未知<br>要求：进门读卡器拨码设置为1，出门读卡器拨码设置为2
        @JsonProperty("ExtEventInOut")
        private Integer extEventInOut;
        // 就地控制器id
        @JsonProperty("ExtEventLocalControllerID")
        private Integer extEventLocalControllerID;
        // 主设备拨码
        @JsonProperty("ExtEventMainDevID")
        private Integer extEventMainDevID;
        // 人员编号
        @JsonProperty("ExtEventPersonNo")
        private String extEventPersonNo;
        // 图片的url
        @JsonProperty("ExtEventPictureURL")
        private String extEventPictureURL;
        // 读卡器id
        @JsonProperty("ExtEventReaderID")
        private Integer extEventReaderID;
        // 读卡器类别 0-无效<br>1-IC读卡器<br>2-身份证读卡器<br>3-二维码读卡器<br>4-指纹头
        @JsonProperty("ExtEventReaderKind")
        private Integer extEventReaderKind;
        // 报告上传通道 1-布防上传<br>2-中心组1上传<br>3-中心组2上传<br>0-无效
        @JsonProperty("ExtEventReportChannel")
        private Integer extEventReportChannel;
        // 群组编号
        @JsonProperty("ExtEventRoleID")
        private Integer extEventRoleID;
        // 分控制器硬件ID
        @JsonProperty("ExtEventSubDevID")
        private Integer extEventSubDevID;
        // 刷卡次数
        @JsonProperty("ExttEventSwipNum")
        private Integer extEventSwipNum;
        // 事件类型
        @JsonProperty("ExtEventType")
        private Integer extEventType;
        // 多重认证序号
        @JsonProperty("ExtEventVerifyID")
        private Integer extEventVerifyID;
        // 白名单单号
        @JsonProperty("ExtEventWhiteListNo")
        private Integer extEventWhiteListNo;
        // 事件上报驱动的时间
        @JsonProperty("ExtReceiveTime")
        private String extReceiveTime;
        // 事件流水号
        @JsonProperty("Seq")
        private String seq;
        // 用户类型 0 未知，1 普通，2 来宾，3 黑名单，4 管理员
        @JsonProperty("UerType")
        private Integer uerType;
        // 图片服务器唯一编码
        @JsonProperty("svrIndexCode")
        private String svrIndexCode;

    }

    /**
     * 人员身份证信息
     */
    @lombok.Data
    public static class EventIdentityCardInfo{
        // 住址
        @JsonProperty("Address")
        private String address;
        // 出生日期
        @JsonProperty("Birth")
        private String birth;
        // 有效日期结束时间
        @JsonProperty("EndDate")
        private String endDate;
        // 身份证id
        @JsonProperty("IdNum")
        private String idNum;
        // 签发机关
        @JsonProperty("IssuingAuthority")
        private String issuingAuthority;
        // 姓名
        @JsonProperty("Name")
        private String name;
        // 民族
        @JsonProperty("Nation")
        private Integer nation;
        // 性别
        @JsonProperty("Sex")
        private Integer sex;
        // 有效日期开始时间
        @JsonProperty("StartDate")
        private String startDate;
        // r是否长期有效
        @JsonProperty("TermOfValidity")
        private Integer termOfValidity;
    }

    /**
     * 通道事件信息
     */
    @lombok.Data
    public static class EventCustomerNumInfo{
        // 通道号
        @JsonProperty("AccessChannel")
        private Integer accessChannel;
        // 进人数
        @JsonProperty("EntryTimes")
        private Integer entryTimes;
        // 出人数
        @JsonProperty("ExitTimes")
        private Integer exitTimes;
        // 总通行人数
        @JsonProperty("TotalTimes")
        private Integer totalTimes;
    }
}
