package com.work.ppm.commons.artemis.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.work.ppm.application.dto.integratedmanager.PpmZjAuthorityDto;
import com.work.ppm.application.services.integratedmanager.impl.PpmZjAuthorityService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.artemis.ArtemisPageInfo;
import com.work.ppm.commons.artemis.ArtemisResponse;
import com.work.ppm.commons.artemis.enums.ControlTypeEnums;
import com.work.ppm.commons.artemis.enums.EventTypeEnums;
import com.work.ppm.commons.artemis.enums.RestStateEnums;
import com.work.ppm.commons.artemis.request.*;
import com.work.ppm.commons.artemis.response.*;
import com.work.ppm.commons.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 海康卫视闸机服务
 */
@Slf4j
@Component
public class ArtemisService implements InitializingBean {

    private static final String ARTEMIS_PATH = "/artemis";
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    // 配置文件需要添加
    @Value("${artemis.host}")
    private String host;
    @Value("${artemis.app-key}")
    private String appKey;
    @Value("${artemis.app-secret}")
    private String appSecret;
    @Value("${artemis.subscribe-url}")
    private String subscribeUrl;
    @Lazy
    @Autowired
    private PpmZjAuthorityService zjAuthorityService;

    @Override
    public void afterPropertiesSet() throws Exception {
        ArtemisConfig.host = this.host;
        ArtemisConfig.appKey = this.appKey;
        ArtemisConfig.appSecret = this.appSecret;

//        this.eventSubscribe();// 启动时发起事件订阅
    }

    /**
     * 查询门禁点列表v2
     *
     * @return
     */
    public ArtemisPageInfo<DoorResp> getDoorV2(DoorReqt doorReqt) {
        final String getCamsApi = ARTEMIS_PATH + "/api/resource/v2/door/search";
        Map<String, String> path = new HashMap<String, String>(2) {
            {//根据现场环境部署确认是http还是https
                put("https://", getCamsApi);
            }
        };
        String body = JSON.toJSONString(doorReqt);
        ArtemisPageInfo<DoorResp> artemisPageInfo = null;
        // post请求application/json类型参数
        String context = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE, null);
        ArtemisResponse artemisResponse = JSON.parseObject(context, ArtemisResponse.class);
        if (!RestStateEnums.SUCCESS.getCode().equals(artemisResponse.getCode())) {
            log.error("查询门禁点列表失败：{}", artemisResponse.toString());
            throw new BizException(ApiCode.FAIL, artemisResponse.getMsg());
        }
        artemisPageInfo = artemisResponse.getData().toJavaObject(ArtemisPageInfo.class);
        artemisPageInfo.setResult(artemisPageInfo.getList().toJavaList(DoorResp.class));
        return artemisPageInfo;
    }

    /**
     * 获取人员列表v2
     * <p>
     * 获取人员列表接口可用来全量同步人员信息，返回结果分页展示。
     * </p>
     *
     * @param personReqt 分页对象
     * @return {@link ArtemisPageInfo}
     */
    public ArtemisPageInfo<PersonResp> getPersonV2(PersonReqt personReqt) {
        final String getCamsApi = ARTEMIS_PATH + "/api/resource/v2/person/personList";
        Map<String, String> path = new HashMap<String, String>(2) {
            {//根据现场环境部署确认是http还是https
                put("https://", getCamsApi);
            }
        };
        String body = JSON.toJSONString(personReqt);
        ArtemisPageInfo<PersonResp> artemisPageInfo = null;
        // post请求application/json类型参数
        String context = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE, null);
        ArtemisResponse artemisResponse = JSON.parseObject(context, ArtemisResponse.class);
        if (!RestStateEnums.SUCCESS.getCode().equals(artemisResponse.getCode())) {
            log.error("获取人员列表失败：{}", artemisResponse.toString());
            throw new BizException(ApiCode.FAIL, artemisResponse.getMsg());
        }
        artemisPageInfo = artemisResponse.getData().toJavaObject(ArtemisPageInfo.class);
        artemisPageInfo.setResult(artemisPageInfo.getList().toJavaList(PersonResp.class));
        return artemisPageInfo;
    }

    /**
     * 门禁点反控
     * <p>
     * 该接口支持门常开、门常闭、门开和门闭四种操作。
     * 门常开操作，门会一直处于开状态，不会自动关闭，执行门闭操作，门才会关上；
     * 门常闭操作，门会一直处于关毕状态，普通卡刷卡门不会被打开，执行门开操作，门会打开；
     * 门开操作，执行门打开动作，超过门打开时间，门会自动关上；
     * 门闭操作，执行关门动作，会立即把门关上。
     * 调用该接口，首先要通过获取门禁点资源列表的接口，获取到门禁点唯一编号，然后根据门禁点唯一编号进行反控操作，
     * 该接口支持单个和多个门禁点操作，如果所有门禁点反控操作成功，则返回成功，其他情况都返回失败，在失败的情况下，会
     * 按每个门禁点返回对应的错误。
     * </p>
     * <pre>
     *     {
     *     "doorIndexCodes": [
     *         "1f276203e5234bdca08f7d99e1097bba"
     *     ],
     *     "controlType": 3
     *      }
     * </pre>
     *
     * @param controlReqt 门禁点反控请求参数
     * @return {@link Boolean}
     */
    public void doControl(ControlReqt controlReqt) {
        final String getCamsApi = ARTEMIS_PATH + "/api/acs/v1/door/doControl";
        Map<String, String> path = new HashMap<String, String>(2) {
            {//根据现场环境部署确认是http还是https
                put("https://", getCamsApi);
            }
        };
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("doorIndexCodes", controlReqt.getDoorIndexCodes());
        jsonBody.put("controlType", controlReqt.getControlType().getCode());
        String body = jsonBody.toJSONString();
        // post请求application/json类型参数
        String context = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE);
        log.error("门禁点反控返回报文：{}", context);
        ControlResp controlResp = JSON.parseObject(context, ControlResp.class);
        // 请求是否成功
        if (RestStateEnums.SUCCESS.getCode().equals(controlResp.getCode())) {
            for (ControlResp.ControlResult datum : controlResp.getData()) {
                if (datum.getControlResultCode() != 0) {
                    throw new BizException(ApiCode.FAIL, datum.getControlResultDesc());
                }
            }
        }
    }

    /**
     * 添加权限配置
     * <p>
     * 权限配置支持按组织、人员和设备通道配置权限，适用综合大楼、学校、医院等场景。
     * 说明：权限配置数据采用异步分批入库方式，接口调用成功后返回权限配置单编号，在配置的过程中分批插入数据，
     * 只有当配置单结束时才能查询到完整的权限配置信息。相同的配置数据重复配置时，第一次配置生效后，后面相同的配置将自动过滤丢弃。
     * 合作方配置的tagId用于让多个应用共用出入控制权限服务时，用以区分各自的配置信息。
     * 注意点：不同业务组件设备通道隔离，应该根据业务场景使用不同的设备通道配置权限；如对相同的设备通道都有业务应用，
     * 那么人员隔离，应该根据场景使用不同的人员，否则会造成权限条目归属相互竞争的情况，在权限条目综合查询时，
     * 数据归属以最后一次入库配置为准。 单次接口最多支持1000个设备资源和1000个人员数据。
     * </p>
     * <pre>
     *     {
     *     "personDatas": [
     *         {
     *             "indexCodes": [
     *                 "c35f4a4e-8baa-4e88-b6e3-c4060135e7e6"
     *             ],
     *             "personDataType": "person"
     *         }
     *     ],
     *     "resourceInfos": [
     *         {
     *             "resourceIndexCode": "0a869b06f4ff4da29949acf006405c39",
     *             "resourceType": "acsDevice",
     *             "channelNos": [
     *                 1
     *             ]
     *         }
     *     ],
     *     "startTime": "2018-09-03T17:30:08+08:00",
     *     "endTime": "2018-09-06T17:30:08+08:00"
     * }
     * </pre>
     *
     * @param authAddReqt
     * @return
     */
    public void addAuth(AuthAddReqt authAddReqt) {
        final String getCamsApi = ARTEMIS_PATH + "/api/acps/v1/auth_config/add";
        Map<String, String> path = new HashMap<String, String>(2) {
            {//根据现场环境部署确认是http还是https
                put("https://", getCamsApi);
            }
        };
        Map<String, String> header = Maps.newHashMap();
        header.put("tagId", "acs");//标签
        String body = JSON.toJSONString(authAddReqt);
        log.info("body:{}", body);
        // post请求application/json类型参数
        String context = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE, header);
        log.info("result:{}", context);
        ArtemisResponse artemisResponse = JSON.parseObject(context, ArtemisResponse.class);
        if (!RestStateEnums.SUCCESS.getCode().equals(artemisResponse.getCode())) {
            log.error("添加权限配置失败：{}", artemisResponse.toString());
            throw new BizException(ApiCode.FAIL, artemisResponse.getMsg());
        }
    }

    /**
     * 删除权限配置
     * <p>
     * 根据人员数据、设备通道删除已配置的权限，合作方配置的tagId用于让多个应用共用出入控制权限服务时，用以区分各自的配置信息，
     * 即只能删除同一个tagId的权限配置信息。
     * </p>
     * <pre>
     *     {
     *     "personDatas": [
     *         {
     *             "indexCodes": [
     *                 "59da91f7-0185-4cd2-9f72-013c1243d2f5"
     *             ],
     *             "personDataType": "personGroup"
     *         }
     *     ],
     *     "resourceInfos": [
     *         {
     *             "resourceIndexCode": "0a869b06f4ff4da29949acf006405c39",
     *             "resourceType": "acsDevice",
     *             "channelNos": [
     *                 "1"
     *             ]
     *         }
     *     ]
     * }
     * </pre>
     *
     * @param authDelReqt
     * @return
     */
    public void delAuth(AuthDelReqt authDelReqt) {
        final String getCamsApi = ARTEMIS_PATH + "/api/acps/v1/auth_config/delete";
        Map<String, String> path = new HashMap<String, String>(2) {
            {//根据现场环境部署确认是http还是https
                put("https://", getCamsApi);
            }
        };
        Map<String, String> header = Maps.newHashMap();
        header.put("tagId", "acs");//标签
        String body = JSON.toJSONString(authDelReqt);
        log.info("body:{}", body);
        // post请求application/json类型参数
        String context = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE, header);
        log.info("result:{}", context);
        ArtemisResponse artemisResponse = JSON.parseObject(context, ArtemisResponse.class);
        if (!RestStateEnums.SUCCESS.getCode().equals(artemisResponse.getCode())) {
            log.error("删除权限配置失败：{}", artemisResponse.toString());
            throw new BizException(ApiCode.FAIL, artemisResponse.getMsg());
        }
    }

    /**
     * 查询权限配置
     * <p>
     * 根据人员数据（组织、人员）、设备通道查询已配置的权限，合作方配置的tagId用于让多个应用共用出入控制权限服务时，
     * 用以区分各自的配置信息，即只能查询到同一个tagId的权限配置信息。 接口中人员数据、设备通道、设备通道分组至少一个不为空。
     * acpsV1.5.0版本查询和返回字段新增personExtendData，仅在按人员配置了人员权限扩展参数的情况下才有值。
     * </p>
     * <pre>
     *     {
     *     "personDataIds": [
     *         "59da91f7-0185-4cd2-9f72-013c1243d2f5"
     *     ],
     *     "personDataType": "person",
     *     "resourceInfos": [
     *         {
     *             "resourceIndexCode": "0a869b06f4ff4da29949acf006405c39",
     *             "resourceType": "acsDevice",
     *             "channelNos": [
     *                 1
     *             ]
     *         }
     *     ],
     *     "resourceDataType": "resource",
     *     "pageNo": 1,
     *     "pageSize": 20
     * }
     * </pre>
     *
     * @param authQueryReqt
     * @return
     */
    public ArtemisPageInfo<AuthResp> queryAuth(AuthQueryReqt authQueryReqt) {
        final String getCamsApi = ARTEMIS_PATH + "/api/acps/v1/auth_config/search";
        Map<String, String> path = new HashMap<String, String>(2) {
            {//根据现场环境部署确认是http还是https
                put("https://", getCamsApi);
            }
        };
        Map<String, String> header = Maps.newHashMap();
        header.put("tagId", "acs");//标签
        String body = JSON.toJSONString(authQueryReqt);
        ArtemisPageInfo<AuthResp> artemisPageInfo = null;
        // post请求application/json类型参数
        String context = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE, header);
        ArtemisResponse artemisResponse = JSON.parseObject(context, ArtemisResponse.class);
        if (!RestStateEnums.SUCCESS.getCode().equals(artemisResponse.getCode())) {
            log.error("查询权限配置失败：{}", artemisResponse.toString());
            throw new BizException(ApiCode.FAIL, artemisResponse.getMsg());
        }
        artemisPageInfo = artemisResponse.getData().toJavaObject(ArtemisPageInfo.class);
        artemisPageInfo.setResult(artemisPageInfo.getList().toJavaList(AuthResp.class));
        return artemisPageInfo;
    }

    /**
     * 按事件类型订阅事件
     * <p>该接口用于满足应用方按事件类型码订阅事件，同一个用户重复订阅相同的事件，
     * 接口内部逻辑自动去重，确保不重复投递事件。
     * </p>
     * <pre>
     *     {
     *     "eventTypes": [
     *         131329
     *     ],
     *     "eventDest": "https://ip:port/eventRcv",
     *     "subType": 1,
     *     "eventLvl": [
     *         2
     *     ]
     * }
     * </pre>
     */
    public void eventSubscribe() {
        final String getCamsApi = ARTEMIS_PATH + "/api/eventService/v1/eventSubscriptionByEventTypes";
        Map<String, String> path = new HashMap<String, String>(2) {
            {//根据现场环境部署确认是http还是https
                put("https://", getCamsApi);
            }
        };
        EventReqt eventReqt = EventReqt.builder()
                .eventTypes(Lists.newArrayList(
                        EventTypeEnums.AUTH_PASS.getCode(),
                        EventTypeEnums.OPEN_DOOR.getCode()
                ))
                .eventDest(this.subscribeUrl)
                .build();
        String body = JSON.toJSONString(eventReqt);
        // post请求application/json类型参数
        String context = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, CONTENT_TYPE);
        ArtemisResponse artemisResponse = JSON.parseObject(context, ArtemisResponse.class);
        if (RestStateEnums.SUCCESS.getCode().equals(artemisResponse.getCode())) {
            log.info("按事件类型订阅事件成功！：{}", artemisResponse.toString());
        }
    }

    /**
     * 处理订阅事件
     *
     * @param events
     */
    public void processEvents(List<EventResp.Event> events) {
        if (CollectionUtils.isEmpty(events)) {
            return;
        }
        for (EventResp.Event event : events) {
            // 事件类型
            if (EventTypeEnums.AUTH_PASS.getCode().equals(event.getEventType()) ||
                    EventTypeEnums.OPEN_DOOR.getCode().equals(event.getEventType())) {
                this.processAcsDevice(event.getSrcIndex(), event.getData());
            }
        }
    }

    /**
     * 处理门禁业务
     *
     * @param indexCode
     * @param data
     */
    void processAcsDevice(String indexCode, EventResp.Data data) {
        // 非空判断
        if (ObjectUtils.isEmpty(indexCode) || ObjectUtils.isEmpty(data)) {
            return;
        }
        PpmZjAuthorityDto entry = zjAuthorityService.queryByZjAuth(
                PpmZjAuthorityDto.builder()
                        .certificateNumber(data.getExtEventPersonNo())
                        .ppmZjId(indexCode).build());
        // 权限调目非空和权限未过期时
        if (null != entry) {
            log.info("员工：{} 开启闸机：{}", entry.getEmpname(), entry.getPpmZjId());
            ControlReqt controlReqt = ControlReqt.builder()
                    .doorIndexCodes(Lists.newArrayList(indexCode))
                    .controlType(ControlTypeEnums.OPEN).build();
            this.doControl(controlReqt);
        }
    }
}
