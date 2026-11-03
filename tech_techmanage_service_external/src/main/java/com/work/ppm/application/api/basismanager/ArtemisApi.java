package com.work.ppm.application.api.basismanager;

import com.work.ppm.commons.artemis.response.EventResp;
import com.work.ppm.commons.artemis.service.ArtemisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 门禁API
 */
@Slf4j
@RestController
@RequestMapping("/artemis")
@Api(produces = "application/json;charset=UTF-8", tags = {"ArtemisApi"})
public class ArtemisApi {

    @Autowired
    private ArtemisService artemisService;

    /**
     * 按事件类型订阅事件
     */
    @ApiOperation("按事件类型订阅事件")
    @RequestMapping("/eventSubscription")
    public void subscription(@RequestBody EventResp resp) {
        //非空判断和非事件类订阅判断
        if (ObjectUtils.isEmpty(resp) || !"OnEventNotify".equals(resp.getMethod())) {
            return;
        }
        // 通知方法参数
        EventResp.Params params = resp.getParams();
        if (!ObjectUtils.isEmpty(resp) && "event_acs".equals(params.getAbility())) {
            log.info("门禁订阅事件，时间:{},内容：{}", params.getSendTime(), params.getEvents());
            artemisService.processEvents(params.getEvents());
        }
    }
}
